package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.entity.SessionEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session, Long courseId) {
        String sql = "insert into session (" +
            "created_at," +
            "deleted," +
            "course_id," +
            "fee," +
            "capacity," +
            "image_url," +
            "image_type," +
            "start_date," +
            "end_date," +
            "type," +
            "status" +
            ") values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            session.createdAt(),
            session.deleted(),
            courseId,
            session.fee(),
            session.capacity(),
            session.imageUrl(),
            session.imageType(),
            session.startDate(),
            session.endDate(),
            session.type(),
            session.status()
        );
    }

    @Override
    public SessionEntity findById(Long id) {
        String sessionSql = "select " +
            "id," +
            "created_at," +
            "updated_at," +
            "deleted," +
            "course_id," +
            "fee," +
            "capacity," +
            "image_url," +
            "image_type," +
            "start_date," +
            "end_date," +
            "type," +
            "status " +
            "from session where id = ?";

        RowMapper<SessionEntity> rowMapper = (rs, rowNum) -> SessionEntity.builder()
            .id(rs.getLong("id"))
            .createdAt(toLocalDateTime(rs.getTimestamp("created_at")))
            .updatedAt(toLocalDateTime(rs.getTimestamp("updated_at")))
            .deleted(rs.getBoolean("deleted"))
            .courseId(rs.getLong("course_id"))
            .fee(rs.getLong("fee"))
            .capacity(rs.getInt("capacity"))
            .imageUrl(rs.getString("image_url"))
            .imageType(rs.getString("image_type"))
            .startDate(toLocalDateTime(rs.getTimestamp("start_date")))
            .endDate(toLocalDateTime(rs.getTimestamp("end_date")))
            .type(rs.getString("type"))
            .status(rs.getString("status"))
            .build();

        return jdbcTemplate.queryForObject(sessionSql, rowMapper, id);
    }


    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
