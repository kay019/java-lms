package nextstep.support.fixture;

import nextstep.courses.domain.image.CoverImagePixelSize;

public class CoverImagePixelSizeFixture {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;

    public static CoverImagePixelSize create() {
        return new CoverImagePixelSize(WIDTH, HEIGHT);
    }
}
