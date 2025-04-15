package nextstep.courses.domain.image;

public class CoverImage {
    private final CoverImageFileSize fileSize;
    private final CoverImageExtension extension;
    private final CoverImagePixelSize pixelSize;

    public CoverImage(CoverImageFileSize fileSize, CoverImageExtension extension, CoverImagePixelSize pixelSize) {
        this.fileSize = fileSize;
        this.extension = extension;
        this.pixelSize = pixelSize;
    }
}
