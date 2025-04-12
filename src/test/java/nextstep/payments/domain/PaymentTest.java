package nextstep.payments.domain;

import nextstep.courses.domain.Course;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPeriod;
import nextstep.session.domain.image.SessionCoverImage;
import nextstep.session.domain.image.SessionCoverImageType;
import nextstep.session.domain.payment.SessionPayments;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static nextstep.session.domain.image.RowsTest.dummyRows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PaymentTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        Course course = new Course("test-course-1", 1L);
        SessionPayments payments = new SessionPayments(500_000, 80);
        SessionCoverImage image = new SessionCoverImage(dummyRows(300, 200), SessionCoverImageType.GIF);
        SessionPeriod period = new SessionPeriod(LocalDateTime.now(), LocalDateTime.now());
        session = new Session(1L, course, image, payments, period);
    }

    @DisplayName("Payment 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Payment(1L, session, NsUserTest.JAVAJIGI, 300_000L));
    }

    @DisplayName("두개의 Payment 강의와 유저 정보가 동일 판별")
    @Test
    public void testEqualsSessionUser() {
        Payment payment1 = new Payment(1L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment2 = new Payment(2L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment3 = new Payment(3L, session, NsUserTest.SANJIGI, 200_000L);

        assertAll(
            () -> assertThat(payment1.equalsSessionUser(payment2)).isTrue(),
            () -> assertThat(payment1.equalsSessionUser(payment3)).isFalse()
        );
    }
}
