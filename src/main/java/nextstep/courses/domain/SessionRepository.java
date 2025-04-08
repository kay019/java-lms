package nextstep.courses.domain;

import nextstep.courses.record.SessionRecord;

public interface SessionRepository {
    int save(SessionRecord session);
    SessionRecord findById(long id);
}
