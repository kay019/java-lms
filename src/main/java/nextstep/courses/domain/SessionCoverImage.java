package nextstep.courses.domain;

import java.security.InvalidParameterException;

public class SessionCoverImage {
    private ImageType type;
    private ImageDimension dimension;
    private ImageSize size;

    public SessionCoverImage(String type, int width, int height, int size) {
        this.type = ImageType.fromString(type);
        this.dimension = new ImageDimension(width, height);
        this.size = new ImageSize(size);
    }

    private enum ImageType {
        GIF, JPG, JPEG, PNG, SVG;

        public static ImageType fromString(String imageType) {
            if (imageType == null || imageType.isEmpty()) {
                throw new InvalidParameterException("ImageType cannot be null or empty");
            }

            try {
                return ImageType.valueOf(imageType.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidParameterException(imageType + " is not supported");
            }
        }
    }

    private static class ImageDimension {
        private final int width;
        private final int height;

        public ImageDimension(int width, int height) {
            if (width < 300 || height < 200) {
                throw new InvalidParameterException("width and height must be between 300 and 200");
            }

            if (width * 2 != height * 3) {
                throw new InvalidParameterException("The ratio of width to height should be 3:2.");
            }

            this.width = width;
            this.height = height;
        }
    }

    private static class ImageSize {
        private final int size;

        private ImageSize(int size) {
            if (size > 1024 * 1024) {
                throw new InvalidParameterException("Image size is too large to cover");
            }
            this.size = size;
        }
    }
}
