package nextstep.courses.domain;

public class CoverImage {
    private String type;
    private long size;
    private int width;
    private int height;

    public CoverImage(String type, long size, int width, int height) {
        validCoverImage(type, size, width, height);
        this.type = type;
        this.size = size;
        this.width = width;
        this.height = height;
    }

    private void validCoverImage(String type, long size, int width, int height) {
        if(size > 1024 * 1024){
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다.");
        }
        if(!ImageType.contains(type)){
            throw new IllegalArgumentException("이미지 타입은 gif, jpg(jpeg 포함), png, svg만 허용합니다.");
        }
        if(width < 300 || height < 200){
            throw new IllegalArgumentException("이미지의 width는 300픽셀, height는 200픽셀 이상이어야합니다.");
        }
        if(width*2 != height*3){
            throw new IllegalArgumentException("이미지의 width와 height의 비율은 3:2여야 합니다.");
        }
    }


}
