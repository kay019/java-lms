package nextstep.courses.domain;

import nextstep.courses.domain.session.Session;

import java.util.List;

public interface SessionRepository {
    int save(Session session);

    Session findById(Long sessionId);

    List<Session> findByCourse(Long courseId);
}
