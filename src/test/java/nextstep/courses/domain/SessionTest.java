package nextstep.courses.domain;


import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class SessionTest {

    private Course course;
    private Term term;
    private Cover cover;
    private Session paidSession;
    private Session freeSession;

    @BeforeEach
    void setUp() {
        course = new Course("클린코드", 1L);
        term = new Term(LocalDate.now(), LocalDate.now().plusDays(30));
        cover = new Cover("https://example.com/cover.jpg", "클린코드 커버", new CoverFileSize(100000L), new CoverSize(300, 200), CoverExtension.PNG);
        paidSession = Session.createPaidSession(course, term, cover, 2, 800_000L);
        freeSession = Session.createFreeSession(course, term, cover);
    }

    @Test
    @DisplayName("강의 상태가 모집중일때만 신청 가능하다")
    void enroll() {
        // given
        Payment payment = new Payment(UUID.randomUUID().toString(), paidSession.id(), NsUserTest.JAVAJIGI.getId(), 800_000L);
        // when then
        Assertions.assertThatThrownBy(() -> paidSession.enroll(payment))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("강의 상태가 모집중이면 신청 가능하다")
    void enroll2() {
        // given
        Payment payment = new Payment(UUID.randomUUID().toString(), paidSession.id(), NsUserTest.JAVAJIGI.getId(), 800_000L);
        paidSession.open();

        // when
        paidSession.enroll(payment);

        // then
        Assertions.assertThat(paidSession.isEnrolled(NsUserTest.JAVAJIGI.getId())).isTrue();
    }

    @Test
    @DisplayName("유료 강의는 결제한 금액과 수강료가 일치하지 않으면 수강 신청이 불가능하다.")
    void enroll3() {
        // given
        Payment payment = new Payment(UUID.randomUUID().toString(), paidSession.id(), NsUserTest.JAVAJIGI.getId(), 100_000L);
        paidSession.open();

        // when then
        Assertions.assertThatThrownBy(() -> paidSession.enroll(payment))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("유료 강의는 정원이 가득 차면 수강 신청이 불가능하다.")
    void enroll4() {
        // given
        Payment payment1 = new Payment(UUID.randomUUID().toString(), paidSession.id(), NsUserTest.JAVAJIGI.getId(), 800_000L);
        Payment payment2 = new Payment(UUID.randomUUID().toString(), paidSession.id(), NsUserTest.SANJIGI.getId(), 800_000L);
        paidSession.open();
        paidSession.enroll(payment1);
        paidSession.enroll(payment2);

        // when then
        Assertions.assertThatThrownBy(() -> paidSession.enroll(new Payment(UUID.randomUUID().toString(), paidSession.id(), NsUserTest.SANJIGI.getId(), 800_000L)))
                .isInstanceOf(IllegalStateException.class);
    }
}
