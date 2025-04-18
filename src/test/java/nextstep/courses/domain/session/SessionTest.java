package nextstep.courses.domain.session;

import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.entity.SessionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionTest {

    @DisplayName("Session 인스턴스 생성")
    @Test
    public void testConstructor() {
        SessionConstraint constraint = new SessionConstraint();
        SessionDescriptor descriptor = new SessionDescriptor();

        assertDoesNotThrow(() -> new Session(1L, constraint, descriptor));
    }

    @DisplayName("SessioEntity를 Session 인스턴스로 전환 만들기")
    @Test
    public void testCreateSession() throws IOException {
        SessionEntity sessionEntity = SessionEntity.builder()
            .id(1L)
            .fee(1000L)
            .capacity(30)
            .imageUrl("http://example.com/image.jpg")
            .imageType("JPEG")
            .startDate(LocalDateTime.of(2023, 1, 1, 9, 0))
            .endDate(LocalDateTime.of(2023, 12, 31, 18, 0))
            .type("FREE")
            .status("ENROLLING")
            .build();

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

        Session session = Session.from(sessionEntity, imageHandlerStub);
        assertNotNull(session);
    }
}
