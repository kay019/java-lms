package nextstep.session.service;

import java.util.List;

import nextstep.session.domain.CoverImage;
import nextstep.session.domain.CoverImageRepository;
import nextstep.session.domain.CoverImages;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CoverImageRepository coverImageRepository;

    public SessionService(
        SessionRepository sessionRepository,
        CoverImageRepository coverImageRepository) {
        this.sessionRepository = sessionRepository;
        this.coverImageRepository = coverImageRepository;
    }

    public Session findById(long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        CoverImages coverImages
            = new CoverImages(coverImageRepository.findBySessionId(session.getId()));
        session.addCoverImages(coverImages);
        return session;
    }

    public int save(Session session) {
        int updated = sessionRepository.save(session);

        for (CoverImage coverImage : session.getCoverImages().getCoverImages()) {
            coverImageRepository.save(coverImage);
        }

        return updated;
    }
}
