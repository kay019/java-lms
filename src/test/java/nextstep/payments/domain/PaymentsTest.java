package nextstep.payments.domain;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseTest;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionTest;
import nextstep.session.domain.image.SessionCoverImageTest;
import nextstep.session.domain.type.SessionType;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PaymentsTest {

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

    @DisplayName("Payments 인스턴스 생성 테스트")
    @Test
    public void testConstructor() {
        Payment payment1 = new Payment("1L", session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment2 = new Payment("2L", session, NsUserTest.SANJIGI, 200_000L);
        assertDoesNotThrow(() -> new Payments(List.of(payment1, payment2)));
    }

    @DisplayName("Payments 인스턴스 생성 테스트 - 동일 코스에 대해 한 유저가 두개의 지불을 하는 경우 예외 던짐")
    @Test
    public void testConstructor_duplicatePaymentByUser() {
        Payment payment1 = new Payment("1L", session, NsUserTest.JAVAJIGI, 300_000L);
        Payment payment2 =  new Payment("13", session, NsUserTest.JAVAJIGI, 200_000L);
        assertThatThrownBy(() -> new Payments(List.of(payment1, payment2)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("한 유저가 동일한 코스에 두번 결재할 수 없습니다.");
    }

}
