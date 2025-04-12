package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.session.domain.image.SessionCoverImageTest;
import nextstep.session.domain.type.SessionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionsTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        LocalDateTime now = LocalDateTime.now();
        session = new Session(
            1L,
            new Course(),
            SessionCoverImageTest.I1,
            80,
            200_000L,
            List.of(new Payment()),
            SessionType.PAID,
            now,
            now,
            now,
            now
        );
    }

    @DisplayName("Sessions 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Sessions(List.of(session)));
    }
}
