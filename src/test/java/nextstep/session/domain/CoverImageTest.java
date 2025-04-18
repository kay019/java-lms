package nextstep.session.domain;

import static nextstep.session.domain.CoverImageType.GIF;
import static nextstep.session.domain.CoverImageType.JPG;

public class CoverImageTest {

    public static CoverImage createCoverImage1() {
        return new CoverImage(GIF, 1024 * 1024, 300, 200);
    }

    public static CoverImage createCoverImage2() {
        return new CoverImage(JPG, 1024, 300, 200);
    }

}
