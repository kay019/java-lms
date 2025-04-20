package nextstep.courses.service;

import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static nextstep.courses.domain.SessionTest.PAID_SESSION;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
public class SessionServiceTest {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionRepository sessionRepository;

    Payment payment1;
    Payment payment2;

    @BeforeEach
    void setup() {
        payment1 = new Payment("p1", NsUserTest.JAVAJIGI.getId(), PAID_SESSION.getId(), 1000L);
        payment2 = new Payment("p2", NsUserTest.SANJIGI.getId(), PAID_SESSION.getId(), 1000L);
    }

    @Test
    void 수강신청_정상동작() {
        CoverImage coverImage = new CoverImage("jpg", 1024 * 100, 300, 200);
        Session session = new Session(1L,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                coverImage, false, 1000L, 2, SessionStatus.OPEM);
        sessionRepository.save(session);
        sessionService.enrollUser(session.getId(), payment1);

        Session savedSession = sessionRepository.findById(session.getId()).orElseThrow();
        assertThat(savedSession.getId()).isEqualTo(1L);
        assertThat(savedSession.getEnrolledCount()).isEqualTo(1);
    }

    @Test
    void 수강정원_초과시_예외() {
        CoverImage coverImage = new CoverImage("jpg", 1024 * 100, 300, 200);
        Session session = new Session(null,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                coverImage, false, 1000L, 1, SessionStatus.OPEM);
        sessionRepository.save(session);

        sessionService.enrollUser(session.getId(), payment1);

        assertThatThrownBy(() -> sessionService.enrollUser(session.getId(), payment2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 모집중이_아닌경우_예외() {
        CoverImage coverImage = new CoverImage("jpg", 1024 * 100, 300, 200);
        Session session = new Session(null,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                coverImage, false, 10000L, 2, SessionStatus.CLOSED);
        sessionRepository.save(session);

        assertThatThrownBy(() -> sessionService.enrollUser(session.getId(), payment1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
