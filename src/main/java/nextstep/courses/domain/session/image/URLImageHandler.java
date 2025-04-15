package nextstep.courses.domain.session.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLImageHandler implements ImageHandler {

    private BufferedImage image;
    private final URL url;

    public URLImageHandler(String url) throws IOException {
        this.url = new URL(url);
        this.image = ImageIO.read(this.url);
    }

    @Override
    public BufferedImage image() {
        return image;
    }

    @Override
    public void updateImage() throws IOException {
        this.image = ImageIO.read(url);
    }

    @Override
    public long byteSize() {
        try {
            URLConnection connection = url.openConnection();
            return connection.getContentLengthLong();
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 URL을 확인할 수 없습니다.");
        }
    }
}
