package nextstep.payments.domain;

import nextstep.courses.domain.session.Session;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PaymentTest {

    @DisplayName("Payment 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Payment(1L, new Session(), NsUserTest.JAVAJIGI, 300_000L));
    }

    @DisplayName("두개의 Payment 강의와 유저 정보가 동일 판별")
    @Test
    public void testEqualsSessionUser() {
        Session session = new Session();
        Payment payment1 = new Payment(1L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment2 = new Payment(2L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment3 = new Payment(3L, session, NsUserTest.SANJIGI, 200_000L);

        assertAll(
            () -> assertThat(payment1.equalsSessionUser(payment2)).isTrue(),
            () -> assertThat(payment1.equalsSessionUser(payment3)).isFalse()
        );
    }
}
