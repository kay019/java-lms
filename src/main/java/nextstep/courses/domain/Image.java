package nextstep.courses.domain;

public class Image {
    private String url;
    private ImageSize size;
    private ImageFormat format;

    public Image(String url, int width, int height, long size, String formatStr) {
        ImageFormat imageFormat = ImageFormat.findFormat(formatStr);
        if (imageFormat == ImageFormat.NOT_SUPPORTED) {
            throw new IllegalArgumentException("지원하지 않는 이미지 포맷입니다.");
        }

        ImageSize imageSize = new ImageSize(width, height, size);

        this.url= url;
        this.size = imageSize;
        this.format = imageFormat;
    }

}
