package nextstep.courses.domain.session.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLImageHandler implements ImageHandler {

    @Override
    public BufferedImage image(String url) throws IOException {
        return ImageIO.read(new URL(url));
    }

    @Override
    public long byteSize(String url) {
        try {
            URLConnection connection = new URL(url).openConnection();
            return connection.getContentLengthLong();
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 URL을 확인할 수 없습니다.");
        }
    }
}
