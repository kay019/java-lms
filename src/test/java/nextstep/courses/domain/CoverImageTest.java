package nextstep.courses.domain;

import static org.junit.jupiter.api.Assertions.*;

class CoverImageTest {

    public static CoverImage createCoverImage1() {
        return CoverImageFactory.ofGif(1024 * 1024, 300, 200);
    }

}
