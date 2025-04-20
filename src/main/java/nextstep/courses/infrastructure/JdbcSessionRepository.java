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

}
