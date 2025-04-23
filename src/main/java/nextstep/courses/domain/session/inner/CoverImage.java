package nextstep.courses.domain.session.inner;

public class CoverImage {
    private final Long id;
    private final Long sessionId;
    private final String url;
    private final ImageSize size;
    private final ImageFormat format;

    public CoverImage(Long id, Long sessionId, String url, int width, int height, long size, String formatStr) {
        ImageFormat imageFormat = ImageFormat.findFormat(formatStr);
        validateCheck(imageFormat);

        ImageSize imageSize = new ImageSize(width, height, size);

        this.id = id;
        this.sessionId = sessionId;
        this.url= url;
        this.size = imageSize;
        this.format = imageFormat;
    }

    private void validateCheck(ImageFormat imageFormat){
        if (imageFormat == ImageFormat.NOT_SUPPORTED) {
            throw new IllegalArgumentException("지원하지 않는 이미지 포맷입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return size.getWidth();
    }

    public int getHeight() {
        return size.getHeight();
    }

    public long getSize() {
        return size.getSize();
    }

    public ImageFormat getFormat() {
        return format;
    }
}
