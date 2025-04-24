package nextstep.session.image.repository;

import java.util.List;

import nextstep.session.image.domain.SessionCoverImage;

public interface SessionCoverImageRepository {
    int save(SessionCoverImage sessionCoverImage);
    List<SessionCoverImage> findBySessionId(long sessionId);
}
