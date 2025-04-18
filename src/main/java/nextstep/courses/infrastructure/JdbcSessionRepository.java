package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.entity.SessionEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(SessionEntity sessionEntity) {
        String sql = "INSERT INTO session ("
            + "created_at, deleted, course_id, fee, capacity, "
            + "image_url, image_type, start_date, end_date, type, status"
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
    public SessionEntity findById(Long id) {
        String sessionSql = "SELECT id, created_at, updated_at, deleted, course_id, " +
            "fee, capacity, image_url, image_type, start_date, end_date, type, status " +
            "FROM session WHERE id = ?";

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

    @Override
    public List<SessionEntity> findAllByCourseId(Long courseId) {
        String sql = "SELECT id, created_at, updated_at, deleted, course_id, " +
            "fee, capacity, image_url, image_type, start_date, end_date, type, status " +
            "FROM session WHERE course_id = ?";

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

        return jdbcTemplate.query(sql, rowMapper, courseId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
