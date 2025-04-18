package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.Sessions;
import nextstep.courses.entity.SessionEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
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
    public Long save(Session session, Long courseId) {
        String sql = "INSERT INTO session ("
            + "created_at, deleted, course_id, fee, capacity, "
            + "image_url, image_type, start_date, end_date, type, status"
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SessionEntity sessionEntity = session.to(courseId);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTimestamp(1, Timestamp.valueOf(sessionEntity.getCreatedAt()));
            ps.setBoolean(2, sessionEntity.isDeleted());
            ps.setLong(3, sessionEntity.getCourseId());
            ps.setLong(4, sessionEntity.getFee());
            ps.setInt(5, sessionEntity.getCapacity());
            ps.setString(6, sessionEntity.getImageUrl());
            ps.setString(7, sessionEntity.getImageType());
            ps.setTimestamp(8, Timestamp.valueOf(sessionEntity.getStartDate()));
            ps.setTimestamp(9, Timestamp.valueOf(sessionEntity.getEndDate()));
            ps.setString(10, sessionEntity.getType());
            ps.setString(11, sessionEntity.getStatus());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public int saveAll(Sessions sessions, Long courseId) {
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

        List<SessionEntity> sessionEntities = sessions.to(courseId);

        List<Object[]> batchArgs = sessionEntities.stream()
            .map(sessionEntity -> new Object[]{
                sessionEntity.getCreatedAt(),
                sessionEntity.isDeleted(),
                sessionEntity.getCourseId(),
                sessionEntity.getFee(),
                sessionEntity.getCapacity(),
                sessionEntity.getImageUrl(),
                sessionEntity.getImageType(),
                sessionEntity.getStartDate(),
                sessionEntity.getEndDate(),
                sessionEntity.getType(),
                sessionEntity.getStatus()
            })
            .collect(Collectors.toList());

        int[] result = jdbcTemplate.batchUpdate(sql, batchArgs);
        return Arrays.stream(result).sum();
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
