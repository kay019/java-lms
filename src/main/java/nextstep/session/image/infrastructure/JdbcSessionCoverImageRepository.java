package nextstep.session.image.infrastructure;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import nextstep.session.image.domain.SessionCoverImage;
import nextstep.session.image.entity.SessionCoverImageEntity;
import nextstep.session.image.mapper.SessionCoverImageMapper;
import nextstep.session.image.repository.SessionCoverImageRepository;

@Repository("sessionCoverImageRepository")
public class JdbcSessionCoverImageRepository implements SessionCoverImageRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcSessionCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionCoverImage sessionCoverImage) {
        SessionCoverImageMapper mapper = new SessionCoverImageMapper();
        SessionCoverImageEntity entity = mapper.toEntity(sessionCoverImage);

        String sql = "INSERT INTO session_cover_image (session_id, size, type, width, height) VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
            entity.getSessionId(),
            entity.getSize(),
            entity.getType(),
            entity.getWidth(),
            entity.getHeight());
    }

    @Override
    public SessionCoverImage findById(long id) {
        SessionCoverImageEntity entity = selectSessionCoverImageById(id);
        SessionCoverImageMapper mapper = new SessionCoverImageMapper();
        return mapper.toDomain(entity);
    }

    private SessionCoverImageEntity selectSessionCoverImageById(long id) {
        String sql = "SELECT id, session_id, size, type, width, height FROM session_cover_image WHERE id = ?";

        return jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> new SessionCoverImageEntity(
                rs.getLong("id"),
                rs.getLong("session_id"),
                rs.getInt("size"),
                rs.getString("type"),
                rs.getInt("width"),
                rs.getInt("height")
            ),
            id
        );
    }
}
