package nextstep.support.builder;

import nextstep.courses.domain.EnrollStatus;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.SessionDate;
import nextstep.courses.domain.SessionStatus;
import nextstep.support.fixture.SessionDateFixture;

public class FreeSessionBuilder {
    private Long id = 1L;
    private SessionStatus sessionStatus = SessionStatus.ONGOING;
    private EnrollStatus enrollStatus = EnrollStatus.RECRUITING;
    private SessionDate date = SessionDateFixture.create();

    public FreeSessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public FreeSessionBuilder sessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
        return this;
    }

    public FreeSessionBuilder enrollStatus(EnrollStatus enrollStatus) {
        this.enrollStatus = enrollStatus;
        return this;
    }

    public FreeSession build() {
        return new FreeSession(id, sessionStatus, enrollStatus, date);
    }

}
