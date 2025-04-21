package nextstep.courses.domain.repository;

import nextstep.courses.domain.image.CoverImage;

import java.util.Optional;

public interface CoverImageRepository {
    int save(CoverImage image, Long sessionId);

    Optional<CoverImage> findBySessionId(Long id);

}
