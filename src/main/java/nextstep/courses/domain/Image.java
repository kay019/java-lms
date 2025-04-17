package nextstep.courses.domain;

import nextstep.courses.CannotUploadImageException;

public class Image {
    public static final int MAX_IMAGE_SIZE = 1000;
    public static final int MIN_IMAGE_WIDTH = 300;
    public static final int MIN_IMAGE_HEIGHT = 200;
    public static final double IMAGE_RATIO = 1.5;

    private PositiveNumber size;
    private ImageType imageType;
    private PositiveNumber width;
    private PositiveNumber height;


    public Image(Long size, String imageType, Long width, Long height) {
        this(size, ImageType.of(imageType), width, height);
    }

    public Image(Long size, ImageType imageType, Long width, Long height) {
        validateSize(size);
        validateWidthHeight(width, height);
        this.size = new PositiveNumber(size);
        this.imageType = imageType;
        this.width = new PositiveNumber(width);
        this.height = new PositiveNumber(height);
    }

    private void validateSize(Long size){
        if (size > MAX_IMAGE_SIZE) {
            throw new CannotUploadImageException("이미지의 크기는 1MB 이하여야 합니다.");
        }
    }

    private void validateWidthHeight(Long width, Long height){
        if (width < MIN_IMAGE_WIDTH) {
            throw new CannotUploadImageException("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
        }
        if (height < MIN_IMAGE_HEIGHT) {
            throw new CannotUploadImageException("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
        }
        if (width != height * IMAGE_RATIO) {
            throw new CannotUploadImageException("이미지의 width와 height의 비율은 3:2여야 합니다.");
        }
    }
}
