package nextstep.session.image.repository;

import nextstep.session.image.domain.SessionCoverImage;

public interface SessionCoverImageRepository {
    int save(SessionCoverImage sessionCoverImage);
    SessionCoverImage findById(long id);
}
