package nextstep.courses.domain;

import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.courses.enrollment.domain.EnrollmentStatus;
import nextstep.courses.session.domain.*;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SessionTest {

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 10000L);
    }

    @Test
    void 유료강의_인원초과() {
        Session session = SessionTestFixtures.paidSession(2, 10000L);
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> session.enrollToGeneralCourse(payment, NsUserTest.JAVAJIGI));
    }

    @Test
    void 유료강의_금액_다름() {
        Session session = SessionTestFixtures.paidSession(2, 20000L);
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> session.enrollToGeneralCourse(payment, NsUserTest.JAVAJIGI));
    }

    @Test
    void 무료강의_신청() {
        Session session = SessionTestFixtures.freeSession();
        int initSize = EnrollmentTest.ENROLLMENTS.size();

        session.enrollToGeneralCourse(payment, NsUserTest.JAVAJIGI);
        Assertions.assertThat(EnrollmentTest.ENROLLMENTS.size()).isEqualTo(initSize + 1);
    }

    @Test
    void 모집상태_비모집일_경우_예외() {
        Session session = SessionTestFixtures.nonRecruitSession();
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> session.enrollToGeneralCourse(payment, NsUserTest.JAVAJIGI));
    }

    @Test
    void 선발된_유저는_신청_상태() {
        Session session = SessionTestFixtures.freeSession();
        NsUser user = NsUserTest.JAVAJIGI;
        Payment payment = new Payment();

        Enrollment enrollment = session.enrollToSelectedCourse(payment, user);

        assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.APPLIED);
    }
}
