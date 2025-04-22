package nextstep.courses.domain.repository;

import nextstep.courses.domain.image.CoverImage;

import java.util.Optional;

public interface CoverImageRepository {
    Long save(CoverImage image, Long sessionId);

    Optional<CoverImage> findBySessionId(Long sessionId);

}
