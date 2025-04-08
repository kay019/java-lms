package nextstep.courses.domain;

import static nextstep.courses.domain.CoverImageType.GIF;
import static org.junit.jupiter.api.Assertions.*;

public class CoverImageTest {

    public static CoverImage createCoverImage1() {
        return new CoverImage(GIF, 1024 * 1024, 300, 200);
    }

}
