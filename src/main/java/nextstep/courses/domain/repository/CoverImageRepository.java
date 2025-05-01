package nextstep.courses.domain.repository;

import nextstep.courses.domain.image.CoverImage;

import java.util.List;

public interface CoverImageRepository {
    Long save(CoverImage image, Long sessionId);

    List<CoverImage> findByAllSessionId(Long sessionId);

}
