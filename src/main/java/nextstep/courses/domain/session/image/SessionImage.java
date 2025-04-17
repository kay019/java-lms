package nextstep.courses.domain.session.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SessionImage {
    private static final double WIDTH_RATIO = 3;
    private static final double HEIGHT_RATIO = 2;
    private static final int MAX_BYTE_SIZE = 1024 * 1024;

    private final String url;

    private final ImageHandler imageHandler;

    private final SessionImageType type;

    public SessionImage(String url, SessionImageType type) throws IOException {
        this(url, new URLImageHandler(url), type);
    }

    public SessionImage(ImageHandler imageHandler) {
        this(null, imageHandler, null);
    }

    public SessionImage(String url, ImageHandler imageHandler, SessionImageType type) {
        BufferedImage res = imageHandler.image();
        if ((WIDTH_RATIO * res.getHeight()) != (HEIGHT_RATIO * res.getWidth())) {
            throw new IllegalArgumentException("width와 height의 비율은 3:2 이여야 합니다.");
        }
        if (imageHandler.byteSize() > MAX_BYTE_SIZE) {
            throw new IllegalArgumentException("크기가 1MB를 초과했습니다.");
        }

        this.url = url;
        this.imageHandler = imageHandler;
        this.type = type;
    }

    public String url() {
        return url;
    }

    public String type() {
        return type.toString();
    }

    public BufferedImage image() {
        return imageHandler.image();
    }

    public void updateImage() throws IOException {
        imageHandler.updateImage();
    }
}
