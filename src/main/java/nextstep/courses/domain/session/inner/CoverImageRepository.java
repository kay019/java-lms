package nextstep.courses.domain.session.inner;

public interface CoverImageRepository {
    int save(CoverImage image);

    CoverImage findById(Long id);
}
