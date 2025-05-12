package nextstep.session.infrastructure;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import nextstep.session.domain.EnrollmentStatus;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.SessionStatus;
import nextstep.session.domain.Student;
import nextstep.session.domain.cover.SessionCover;
import nextstep.session.domain.type.FreeSessionType;
import nextstep.session.domain.type.PaidSessionType;
import nextstep.session.domain.type.SessionType;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Session session) {
        String sql = "INSERT INTO session(started_at, ended_at, session_status, enrollment_status, capacity, price) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(session.getStartedAt()));
            ps.setTimestamp(2, Timestamp.valueOf(session.getEndedAt()));
            ps.setString(3, session.getSessionStatus());
            ps.setString(4, session.getEnrollmentStatus());
            ps.setLong(5, session.getCapacity());
            ps.setLong(6, session.getPrice());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        return key != null ? key.longValue() : null;
    }

    @Override
    public Optional<Session> findById(Long sessionId) {
        String joinSql = "SELECT " +
                "s.id, s.started_at, s.ended_at, s.session_status, s.enrollment_status, s.capacity, s.price, " +
                "sc.id as cover_id, sc.session_id as cover_session_id, sc.size, sc.img_type, sc.width, sc.height, " +
                "st.id as student_id, st.ns_user_id, st.create_dt " +
                "FROM session s " +
                "LEFT JOIN session_cover_image sc ON s.id = sc.session_id " +
                "LEFT JOIN student st ON s.id = st.session_id " +
                "WHERE s.id = ?";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(joinSql, sessionId);

        Map<String, Object> firstRow = rows.get(0);

        Long id = getLongValue(firstRow, "id");
        LocalDateTime startAt = toLocalDateTime((Timestamp) firstRow.get("start_at"));
        LocalDateTime endAt = toLocalDateTime((Timestamp) firstRow.get("end_at"));
        SessionStatus sessionStatus = SessionStatus.from((String) firstRow.get("session_status"));
        EnrollmentStatus enrollmentStatus = EnrollmentStatus.from((String) firstRow.get("enrollment_status"));

        Long capacity = getLongValue(firstRow, "capacity");
        Long price = getLongValue(firstRow, "price");

        // 세션 커버 정보
        List<SessionCover> covers = extractSessionCovers(rows);

        // 세션 타입 정보
        SessionType sessionType = getSessionType(price);

        // 학생 정보 추출
        List<Student> students = extractStudents(rows, id);

        // 최종 세션 객체 생성
        Session session = new Session(id, startAt, endAt, covers, sessionType, sessionStatus, enrollmentStatus,
                capacity,
                students);

        return Optional.of(session);
    }

    private List<SessionCover> extractSessionCovers(List<Map<String, Object>> rows) {
        List<SessionCover> covers = new ArrayList<>();
        Set<Long> processedCoverIds = new HashSet<>();

        for (Map<String, Object> row : rows) {
            Long coverId = getLongValue(row, "cover_id");
            if (coverId != null && !processedCoverIds.contains(coverId)) {
                processedCoverIds.add(coverId);

                SessionCover cover = new SessionCover(
                        coverId,
                        getLongValue(row, "cover_session_id"),
                        getIntValue(row, "size"),
                        (String) row.get("img_type"),
                        getIntValue(row, "width"),
                        getIntValue(row, "height")
                );
                covers.add(cover);
            }
        }

        return covers;
    }

    private List<Student> extractStudents(List<Map<String, Object>> rows, Long sessionId) {
        List<Student> students = new ArrayList<>();
        Set<Long> processedStudentIds = new HashSet<>();

        for (Map<String, Object> row : rows) {
            Long studentId = getLongValue(row, "student_id");
            if (studentId != null && !processedStudentIds.contains(studentId)) {
                processedStudentIds.add(studentId);

                Student student = new Student(
                        studentId,
                        new NsUser(getLongValue(row, "ns_user_id")),
                        new Session(sessionId),
                        getBooleanValue(row, "approved"),
                        toLocalDateTime((Timestamp) row.get("create_dt"))
                );
                students.add(student);
            }
        }

        return students;
    }

    private SessionType getSessionType(Long price) {
        if (price > 0) {
            return new PaidSessionType(price);
        }
        return new FreeSessionType();
    }

    private Long getLongValue(Map<String, Object> row, String columnName) {
        Object value = row.get(columnName);
        return value != null ? ((Number) value).longValue() : null;
    }

    private Integer getIntValue(Map<String, Object> row, String columnName) {
        Object value = row.get(columnName);
        return value != null ? ((Number) value).intValue() : null;
    }

    private Boolean getBooleanValue(Map<String, Object> row, String columnName) {
        Object value = row.get(columnName);
        return value != null ? (Boolean) value : null;
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
