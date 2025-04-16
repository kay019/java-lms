package nextstep.courses.domain.session;

import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionDescriptorTest {

    @DisplayName("SessionDescriptor 인스턴스 생성")
    @Test
    public void testConstructor() {
        ImageHandler imageHandlerStub = new ImageHandler() {
            @Override
            public BufferedImage image() {
                return new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public void updateImage() {
            }

            @Override
            public long byteSize() {
                return 1024L * 866L;
            }
        };

        assertDoesNotThrow(() -> new SessionDescriptor(new SessionImage(imageHandlerStub), new SessionPeriod(), new SessionEnrollPolicy()));
    }
}
