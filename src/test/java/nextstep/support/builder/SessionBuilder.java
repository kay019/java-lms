package nextstep.support.builder;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionDate;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.enroll.FreeSessionEnroll;
import nextstep.courses.domain.enroll.PaidSessionEnroll;
import nextstep.courses.domain.enroll.SessionEnroll;
import nextstep.courses.domain.image.CoverImage;
import nextstep.support.fixture.CoverImageFixture;
import nextstep.support.fixture.SessionDateFixture;

public class SessionBuilder {
    private Long id = 1L;
    private CoverImage coverImage = CoverImageFixture.create();
    private SessionStatus status = SessionStatus.RECRUITING;
    private SessionDate date = SessionDateFixture.create();
    private SessionEnroll enroll;

    public SessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public SessionBuilder coverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public SessionBuilder free() {
        this.enroll = new FreeSessionEnroll();
        return this;
    }

    public SessionBuilder paid(int fee, int maxStudent) {
        this.enroll = new PaidSessionEnroll(fee, maxStudent);
        return this;
    }

    public SessionBuilder status(SessionStatus status) {
        this.status = status;
        return this;
    }

    public Session build() {
        return new Session(id, coverImage, enroll, status, date);
    }

}
