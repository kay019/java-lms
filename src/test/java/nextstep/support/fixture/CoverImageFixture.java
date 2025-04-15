package nextstep.support.fixture;

import nextstep.courses.domain.image.CoverImage;
import nextstep.courses.domain.image.CoverImageExtension;
import nextstep.courses.domain.image.CoverImageFileSize;
import nextstep.courses.domain.image.CoverImagePixelSize;

public class CoverImageFixture {
    private static final CoverImageFileSize FILE_SIZE = CoverImageFileSizeFixture.create();
    private static final CoverImageExtension EXTENSION = CoverImageExtension.GIF;
    private static final CoverImagePixelSize PIXEL_SIZE = CoverImagePixelSizeFixture.create();

    public static CoverImage create() {
        return new CoverImage(
                FILE_SIZE, EXTENSION, PIXEL_SIZE
        );
    }
}
