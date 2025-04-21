package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

class SessionTest {

  @Test
  void 유료_강의는_최대_수강_인원을_초과할_수_없다() {
    Session session = new Session(
        LocalDateTime.now(),
        LocalDateTime.now().plusDays(1),
        new SessionImageInfo(ImageType.PNG, 1024, 300, 200),
        false,
        1000,
        2,
        SessionStatus.RECRUITING
    );

    session.enroll(new NsUser(), 1000L);
    session.enroll(new NsUser(), 1000L);

    assertThatThrownBy(() -> session.enroll(new NsUser(), 1000))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void 강의_상태가_모집중이_아니면_수강신청_불가() {
    Session session = new Session(
        LocalDateTime.now(),
        LocalDateTime.now().plusDays(1),
        new SessionImageInfo(ImageType.PNG, 1024, 300, 200),
        true,
        0,
        0,
        SessionStatus.OPEN
    );

    assertThatThrownBy(() -> session.enroll(new NsUser(), 0))
        .isInstanceOf(IllegalStateException.class);
  }
}