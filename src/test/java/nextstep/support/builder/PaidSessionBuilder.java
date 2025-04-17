package nextstep.support.builder;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.SessionDate;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.image.CoverImage;
import nextstep.support.fixture.CoverImageFixture;
import nextstep.support.fixture.SessionDateFixture;

public class PaidSessionBuilder {
    private Long id = 1L;
    private CoverImage coverImage = CoverImageFixture.create();
    private SessionStatus status = SessionStatus.RECRUITING;
    private SessionDate date = SessionDateFixture.create();
    private int fee = 10_000;
    private int maxStudent = 2;

    public PaidSessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PaidSessionBuilder coverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public PaidSessionBuilder status(SessionStatus status) {
        this.status = status;
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
        return new PaidSession(id, coverImage, status, date, fee, maxStudent);
    }

}
