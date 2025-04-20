package nextstep.session.image.domain;

import nextstep.exception.SessionCoverImageIllegalArgumentException;

public class SessionCoverImage {
    private final long id;
    private final long courseId;
    private final ImageFileSize fileSize;
    private final ImageType type;
    private final ImageSize imageSize;

    public SessionCoverImage(long id, long courseId, ImageFileSize fileSize, ImageType type, ImageSize imageSize) {
        this.courseId = courseId;
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

    public long getCourseId() {
        return courseId;
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
