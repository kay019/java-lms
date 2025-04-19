package nextstep.courses.domain.session;

import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;
import nextstep.stub.TestImageHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionDescriptorTest {

    @DisplayName("SessionDescriptor 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionDescriptor(
            new SessionImage(new TestImageHandler(300, 200, 1024L * 866L)),
            new SessionPeriod(),
            new SessionEnrollPolicy()
        ));
    }
}
