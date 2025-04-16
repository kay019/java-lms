package nextstep.courses.domain.session.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageHandler {
    BufferedImage image();

    void updateImage() throws IOException;

    long byteSize();
}
