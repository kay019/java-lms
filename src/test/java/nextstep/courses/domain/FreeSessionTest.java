package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FreeSessionTest {

    @Test
    @DisplayName("무료 세션 생성 테스트")
    public void createFreeSession() {
        // given & when
        FreeSession freeSession = new FreeSession();
        
        // then
        assertThat(freeSession.getType()).isEqualTo(SessionType.FREE);
    }
    
    @Test
    @DisplayName("무료 세션 수강 신청 성공 테스트")
    public void addRegistrationSuccess() {
        // given
        FreeSession freeSession = new FreeSession(SessionStatus.RECRUITING);
        
        NsUser user = new NsUser(1L, "user1", "password", "User One", "user1@example.com");
        // Free sessions don't need payment validation
        List<Payment> payments = Collections.emptyList();
        
        // when
        Registration registration = freeSession.addRegistration(freeSession, user, payments);
        
        // then
        assertThat(registration).isNotNull();
    }
    
    @Test
    @DisplayName("모집중 상태가 아닌 경우 수강 신청 실패 테스트")
    public void addRegistrationFailsWhenNotRecruiting() {
        // given
        FreeSession freeSession = new FreeSession(SessionStatus.PREPARING);
        
        NsUser user = new NsUser(1L, "user1", "password", "User One", "user1@example.com");
        List<Payment> payments = Collections.emptyList();
        
        // when & then
        assertThatThrownBy(() -> freeSession.addRegistration(freeSession, user, payments))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("강의 상태가 모집중일 때만 수강 신청이 가능합니다");
    }
}
