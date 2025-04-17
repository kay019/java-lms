package nextstep.courses.domain;

import nextstep.exception.SessionCoverImageIllegalArgumentException;

public class SessionCoverImage {
    private final long id;
    private final ImageFileSize fileSize;
    private final ImageType type;
    private final ImageSize imageSize;

    public SessionCoverImage(long id, ImageFileSize fileSize, ImageType type, ImageSize imageSize) {
        validate(fileSize, type, imageSize);

        this.id = id;
        this.fileSize = fileSize;
        this.type = type;
        this.imageSize = imageSize;
    }

    private void validate(ImageFileSize fileSize, ImageType type, ImageSize imageSize) {
        if (fileSize == null || type == null || imageSize == null) {
            throw new SessionCoverImageIllegalArgumentException();
        }
    }

    public long getId() {
        return id;
    }

    public ImageFileSize getFileSize() {
        return fileSize;
    }

    public ImageType getType() {
        return type;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }
}
