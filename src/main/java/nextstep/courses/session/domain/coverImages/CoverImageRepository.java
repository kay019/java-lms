package nextstep.courses.session.domain.coverImages;

public interface CoverImageRepository {
    int save(SessionCoverImage image);

    SessionCoverImages findBySessionId(Long id);
}
