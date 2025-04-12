package nextstep.session.domain;

import static nextstep.session.domain.CoverImageType.GIF;

public class CoverImageTest {

    public static CoverImage createCoverImage1() {
        return new CoverImage(GIF, 1024 * 1024, 300, 200);
    }

}
