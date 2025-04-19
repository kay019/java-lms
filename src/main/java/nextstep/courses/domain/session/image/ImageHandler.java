package nextstep.courses.domain.session.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageHandler {
    BufferedImage image(String url) throws IOException;

    long byteSize(String url) throws IOException;
}
