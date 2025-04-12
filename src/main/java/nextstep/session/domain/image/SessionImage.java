package nextstep.session.domain.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SessionImage {
    private static final double WIDTH_RATIO = 3;
    private static final double HEIGHT_RATIO = 2;
    private static final int MAX_BYTE_SIZE = 1024 * 1024;

    private String urlStr;

    private ImageHandler imageHandler;

    private SessionImageType type;

    public SessionImage() {
    }

    public SessionImage(String urlStr, ImageHandler imageHandler, SessionImageType type) {
        BufferedImage res = imageHandler.getImage();
        if ((WIDTH_RATIO * res.getHeight()) != (HEIGHT_RATIO * res.getWidth())) {
            throw new IllegalArgumentException("width와 height의 비율은 3:2 이여야 합니다.");
        }
        if (imageHandler.byteSize() > MAX_BYTE_SIZE) {
            throw new IllegalArgumentException("크기가 1MB를 초과했습니다.");
        }

        this.urlStr = urlStr;
        this.imageHandler = imageHandler;
        this.type = type;
    }

    public BufferedImage image() {
        return imageHandler.getImage();
    }

    public void updateImage() throws IOException {
        imageHandler.updateImage();
    }
}
