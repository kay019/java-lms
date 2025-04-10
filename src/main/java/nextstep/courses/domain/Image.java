package nextstep.courses.domain;

import nextstep.qna.CannotDeleteException;

public class Image {
    private int size;
    private ImageType imageType;
    private int width;
    private int height;

    public Image(int size, String imageType, int width, int height) {
        this(size, ImageType.of(imageType), width, height);
    }

    public Image(int size, ImageType imageType, int width, int height) {
        validateSize(size);
        validateWidthHeight(width, height);
        this.size = size;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
    }

    private void validateSize(int size){
        if (size > 1000) {
            throw new CannotUploadImageException("이미지의 크기는 1MB 이하여야 합니다.");
        }
    }

    private void validateWidthHeight(int width, int height){
        if (width < 300) {
            throw new CannotUploadImageException("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
        }
        if (height < 200) {
            throw new CannotUploadImageException("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
        }
        if (width * 2 != height * 3) {
            throw new CannotUploadImageException("이미지의 width와 height의 비율은 3:2여야 합니다.");
        }
    }
}
