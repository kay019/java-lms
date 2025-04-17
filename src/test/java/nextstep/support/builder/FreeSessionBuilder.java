package nextstep.support.builder;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.SessionDate;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.image.CoverImage;
import nextstep.support.fixture.CoverImageFixture;
import nextstep.support.fixture.SessionDateFixture;

public class FreeSessionBuilder {
    private Long id = 1L;
    private CoverImage coverImage = CoverImageFixture.create();
    private SessionStatus status = SessionStatus.RECRUITING;
    private SessionDate date = SessionDateFixture.create();

    public FreeSessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public FreeSessionBuilder coverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public FreeSessionBuilder status(SessionStatus status) {
        this.status = status;
        return this;
    }

    public FreeSession build() {
        return new FreeSession(id, coverImage, status, date);
    }

}
