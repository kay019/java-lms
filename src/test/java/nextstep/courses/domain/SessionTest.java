package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SessionTest {

    public static final CoverImage coverImage = new CoverImage("gif", 1024 * 1024, 300, 200);
    public static final Session FREE_SESSION = new Session(1L, coverImage, true, 0, 1, SessionStatus.OPEM);
    public static final Session PAID_SESSION = new Session(2L, coverImage, false, 1000L, 1, SessionStatus.OPEM);


    @Test
    void 무료_강의_정원_무제한() {
        FREE_SESSION.enroll(new Payment("p1", NsUserTest.JAVAJIGI.getId(), FREE_SESSION.getId(), 0L));
        FREE_SESSION.enroll(new Payment("p2", NsUserTest.SANJIGI.getId(), FREE_SESSION.getId(), 0L));
        assertThat(FREE_SESSION.getEnrolledCount()).isEqualTo(2);
    }

    @Test
    void 유료_강의_정원_초과_불가() {
        PAID_SESSION.enroll(new Payment("p1", NsUserTest.JAVAJIGI.getId(), FREE_SESSION.getId(), 1000L));
        assertThatThrownBy(() -> PAID_SESSION.enroll(new Payment("p2", NsUserTest.SANJIGI.getId(), PAID_SESSION.getId(), 1000L))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유료_강의_결제_금액_다름() {
        assertThatThrownBy(() -> PAID_SESSION.enroll(new Payment("p1", NsUserTest.JAVAJIGI.getId(), PAID_SESSION.getId(), 1000L))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유료_강의_모집중_아님() {
        assertThatThrownBy(() -> PAID_SESSION.enroll(new Payment("p1", NsUserTest.JAVAJIGI.getId(), PAID_SESSION.getId(), 1000L))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유료_강의_수강신청_성공() {
        PAID_SESSION.enroll(new Payment("p1", NsUserTest.JAVAJIGI.getId(), PAID_SESSION.getId(), 1000L));
        assertThat(PAID_SESSION.getEnrolledCount()).isEqualTo(1);

    }


}
