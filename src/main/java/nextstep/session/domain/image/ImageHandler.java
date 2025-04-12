package nextstep.session.domain.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageHandler {
    BufferedImage getImage();

    void updateImage() throws IOException;

    long byteSize();
}
