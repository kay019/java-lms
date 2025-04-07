package nextstep.courses.domain;

public class CoverImage {

    private final CoverImageFileSize size;
    private final CoverImageType type;
    private final CoverImageResolution resolution;

    CoverImage(CoverImageType type, long size, int width, int height) {
        this.size = new CoverImageFileSize(size);
        this.type = type;
        this.resolution = new CoverImageResolution(width, height);
    }
}
