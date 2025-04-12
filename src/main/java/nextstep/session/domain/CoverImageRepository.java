package nextstep.session.domain;

import java.util.List;

public interface CoverImageRepository {
    int save(CoverImage coverImage);
    CoverImage findById(long id);
    List<CoverImage> findBySessionId(long sessionId);
}
