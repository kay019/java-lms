package nextstep.courses.infrastructure;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.Period;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcSessionRepository implements SessionRepository {

    JdbcTemplate jdbcTemplate;

    public JdbcSessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Session> findById(Long id) {
        Session session = jdbcTemplate.queryForObject("select * from session where id = ?", (rs, rowNum) -> {
            Image image = jdbcTemplate.queryForObject(
                "select * from image where id = ?", (image_rs, image_rowNum) -> new Image(image_rs.getString("url"), image_rs.getInt("width"), image_rs.getInt("height"), image_rs.getString("format"), image_rs.getInt("size")), rs.getLong("image_id"));
            return new FreeSession(image,
                new Period(rs.getTimestamp("start_at").toLocalDateTime().toLocalDate(), rs.getTimestamp("end_at").toLocalDateTime().toLocalDate()));
        }, id);

        return Optional.ofNullable(session);
    }

    @Override
    public int save(Session session) {
        // save image
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO image (url, width, height, format, size) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, session.getCoverImage().getUrl());
            ps.setInt(2, session.getCoverImage().getWidth());
            ps.setInt(3, session.getCoverImage().getHeight());
            ps.setString(4, session.getCoverImage().getFormat());
            ps.setLong(5, session.getCoverImage().getSize());
            return ps;
        }, keyHolder);

        Long imageId = keyHolder.getKey().longValue();

        String sql = "INSERT INTO session (image_id, start_at, end_at, price, max_participants, status) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
            sql,
            imageId,
            session.getPeriod().getStartDate(),
            session.getPeriod().getEndDate(),
            null,
            null,
            session.getStatus().name()
        );
    }

}
