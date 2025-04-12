package nextstep.session.domain;

public interface CoverImageRepository {
    int save(CoverImage coverImage);
    CoverImage findById(long id);
}
