package nextstep.session.domain.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlImageHandler implements ImageHandler {
    private final URL url;

    public UrlImageHandler(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    @Override
    public BufferedImage download() throws IOException {
        return ImageIO.read(url);
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
