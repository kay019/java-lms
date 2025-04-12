package nextstep.session.domain.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageHandler {
    BufferedImage download() throws IOException;
    long byteSize();
}
