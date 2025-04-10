package nextstep.courses.domain;

public class Image {
    private int size;
    private ImageType imageType;
    private int width;
    private int height;

    public Image(int size, ImageType imageType, int width, int height) {
        this.size = size;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
    }
}
