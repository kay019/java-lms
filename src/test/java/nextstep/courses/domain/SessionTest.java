package nextstep.courses.domain;

import nextstep.courses.session.domain.*;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SessionTest {

    private SessionDate sessionDate;
    private SessionCoverImage sessionCoverImage;
    private Payment payment;

    @BeforeEach
    void setUp() {
        sessionDate = new SessionDate(LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 0, 0));
        sessionCoverImage = new SessionCoverImage(1, SessionImageType.from("gif"), 300, 200);
        payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 10000L);
    }

    @Test
    void 유료강의_인원초과() {
        Session session = SessionTestFixtures.paidSession(2, 10000L);
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> session.enroll(payment, NsUserTest.JAVAJIGI));
    }

    @Test
    void 유료강의_금액_다름() {
        Session session = SessionTestFixtures.paidSession(2, 20000L);
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> session.enroll(payment, NsUserTest.JAVAJIGI));
    }

    @Test
    void 무료강의_신청() {
        Session session = SessionTestFixtures.freeSession();
        int initSize = EnrollmentTest.ENROLLMENTS.size();

        session.enroll(payment, NsUserTest.JAVAJIGI);
        Assertions.assertThat(EnrollmentTest.ENROLLMENTS.size()).isEqualTo(initSize + 1);
    }
}
