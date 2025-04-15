package nextstep.support.fixture;

import nextstep.courses.domain.image.CoverImageFileSize;

public class CoverImageFileSizeFixture {
    private static final long SIZE = 1024;

    public static CoverImageFileSize create() {
        return new CoverImageFileSize(SIZE);
    }

}
