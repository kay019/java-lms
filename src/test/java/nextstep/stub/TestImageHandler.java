package nextstep.stub;

import nextstep.courses.domain.session.image.ImageHandler;

import java.awt.image.BufferedImage;

public class TestImageHandler implements ImageHandler {
    private int width;
    private int height;
    private long byteSize;

    public TestImageHandler(int width, int height, long byteSize) {
        this.width = width;
        this.height = height;
        this.byteSize = byteSize;
    }

    @Override
    public BufferedImage image(String url) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public long byteSize(String url) {
        return byteSize;
    }
}
