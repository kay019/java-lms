package nextstep.support.builder;

import nextstep.courses.domain.EnrollStatus;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.SessionDate;
import nextstep.courses.domain.SessionStatus;
import nextstep.support.fixture.SessionDateFixture;

public class PaidSessionBuilder {
    private Long id = 1L;
    private SessionStatus sessionStatus = SessionStatus.ONGOING;
    private EnrollStatus enrollStatus = EnrollStatus.RECRUITING;
    private SessionDate date = SessionDateFixture.create();
    private int fee = 10_000;
    private int maxStudent = 2;

    public PaidSessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PaidSessionBuilder sessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
        return this;
    }

    public PaidSessionBuilder enrollStatus(EnrollStatus enrollStatus) {
        this.enrollStatus = enrollStatus;
        return this;
    }

    public PaidSessionBuilder fee(int fee) {
        this.fee = fee;
        return this;
    }

    public PaidSessionBuilder maxStudent(int maxStudent) {
        this.maxStudent = maxStudent;
        return this;
    }

    public PaidSession build() {
        return new PaidSession(id, sessionStatus, enrollStatus, date, fee, maxStudent);
    }

}
