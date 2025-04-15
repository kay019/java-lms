package nextstep.payments.domain;

import nextstep.courses.domain.session.Session;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PaymentsTest {


    @DisplayName("Payments 인스턴스 생성 테스트")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Payments());
    }

    @DisplayName("payment 추가")
    @Test
    public void testAdd() {
        Payment payment = new Payment(1L, new Session(), NsUserTest.JAVAJIGI, 200_000L);
        Payments payments = new Payments();
        assertDoesNotThrow(() -> payments.add(payment));
    }

    @DisplayName("payment 추가 - 동일 유저가 한 코드에 대해서 결재를 여러번 하면 예외 던짐")
    @Test
    public void testAdd_throwException() {
        Session session = new Session();
        Payment payment1 = new Payment(1L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment2 = new Payment(2L, session, NsUserTest.JAVAJIGI, 200_000L);

        Payments payments = new Payments();
        assertAll(
            () -> assertDoesNotThrow(() -> payments.add(payment1)),
            () -> assertThatThrownBy(() -> payments.add(payment2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("한 유저가 동일한 코스에 두번 결재할 수 없습니다.")
        );
    }
}
