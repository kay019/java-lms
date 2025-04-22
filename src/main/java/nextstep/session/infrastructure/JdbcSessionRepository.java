package nextstep.session.infrastructure;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nextstep.enrollment.domain.Student;
import nextstep.enrollment.entity.StudentEntity;
import nextstep.enrollment.mapper.StudentMapper;
import nextstep.session.domain.Session;
import nextstep.session.entity.SessionEntity;
import nextstep.session.mapper.SessionMapper;
import nextstep.session.repository.SessionRepository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private final int SESSION_ID = 1;
    private final int USER_ID = 2;
    private final int NAME = 3;

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public int save(Session session) {
        SessionMapper mapper = new SessionMapper();
        SessionEntity entity = mapper.toEntity(session);

        int sessionResult = saveSession(entity);
        int[] enrollmentResults = saveEnrollments(entity);
        return sessionResult + Arrays.stream(enrollmentResults).sum();
    }

    private int saveSession(SessionEntity entity) {
        String sql = "INSERT INTO session (id, course_id, status, fee, capacity, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
            entity.getId(),
            entity.getCourseId(),
            entity.getStatus(),
            entity.getFee(),
            entity.getCapacity(),
            entity.getStartDate(),
            entity.getEndDate());
    }

    private int[] saveEnrollments(SessionEntity entity) {
        String sql = "INSERT INTO student (session_id, user_id, name) VALUES (?, ?, ?)";

        return jdbcTemplate.batchUpdate(sql,
            new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    StudentEntity studentEntity = entity.getStudentEntities().get(i);
                    ps.setLong(SESSION_ID, studentEntity.getSessionId());
                    ps.setLong(USER_ID, studentEntity.getUserId());
                    ps.setString(NAME, studentEntity.getName());
                }

                @Override
                public int getBatchSize() {
                    return entity.getStudentEntities().size();
                }
            });
    }

    @Override
    public Session findById(long id) {
        List<Student> students = selectStudents(id);
        SessionEntity sessionEntity = selectSession(id, students);

        if (sessionEntity == null) {
            return null;
        }

        SessionMapper sessionMapper = new SessionMapper();
        return sessionMapper.toDomain(sessionEntity);
    }

    private SessionEntity selectSession(long id, List<Student> students) {
        String sql = "SELECT id, course_id, status, fee, capacity, start_date, end_date FROM session WHERE id = ?";

        return jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> new SessionEntity(
                rs.getLong("id"),
                rs.getLong("course_id"),
                rs.getString("status"),
                rs.getInt("fee"),
                rs.getInt("capacity"),
                rs.getTimestamp("start_date").toLocalDateTime().toLocalDate(),
                rs.getTimestamp("end_date").toLocalDateTime().toLocalDate(),
                students
            ),
            id
        );
    }

    private List<Student> selectStudents(long id) {
        StudentMapper mapper = new StudentMapper();
        String sql = "SELECT id, user_id, session_id, name FROM student WHERE session_id = ?";

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new StudentEntity(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("session_id"),
                rs.getString("name")
            ),
            id
        ).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
