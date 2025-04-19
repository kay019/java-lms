package nextstep.courses.domain.session;

import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.image.SessionImageType;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {

    @DisplayName("Session 인스턴스 생성")
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

        SessionConstraint constraint = new SessionConstraint(100, 80);
        SessionDescriptor descriptor = new SessionDescriptor(
            new SessionImage("http://test", imageHandlerStub, SessionImageType.JPEG),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
            new SessionEnrollPolicy()
        );

        assertDoesNotThrow(() -> new Session("1", constraint, descriptor));
    }
}
