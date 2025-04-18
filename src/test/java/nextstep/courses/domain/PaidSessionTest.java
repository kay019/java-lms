package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class PaidSessionTest {
    
    @Test
    @DisplayName("유료 세션 생성 테스트")
    public void createPaidSession() {
        // given
        int maxParticipants = 20;
        Long fee = 50000L;
        List<Registration> registrations = new ArrayList<>();
        
        // when
        PaidSession paidSession = new PaidSession(maxParticipants, fee, registrations);
        
        // then
        assertThat(paidSession.getType()).isEqualTo(SessionType.PAID);
    }
    
    @Test
    @DisplayName("유료 세션 수강 신청 성공 테스트")
    public void addRegistrationSuccess() {
        // given
        int maxParticipants = 20;
        Long fee = 50000L;
        List<Registration> registrations = new ArrayList<>();
        
        PaidSession paidSession = new PaidSession(maxParticipants, fee, registrations, SessionStatus.RECRUITING);
        
        NsUser user = new NsUser(1L, "user1", "password", "User One", "user1@example.com");
        List<Payment> payments = List.of(new Payment("payment1", 1L, user.getId(), fee));
        
        // when
        Registration registration = paidSession.addRegistration(paidSession, user, payments);
        
        // then
        assertThat(registration).isNotNull();
    }
    
    @Test
    @DisplayName("여러 결제의 합계가 수강료와 일치하는 경우 수강 신청 성공 테스트")
    public void addRegistrationSuccessWithMultiplePayments() {
        // given
        int maxParticipants = 20;
        Long fee = 50000L;
        List<Registration> registrations = new ArrayList<>();
        
        PaidSession paidSession = new PaidSession(maxParticipants, fee, registrations, SessionStatus.RECRUITING);
        
        NsUser user = new NsUser(1L, "user1", "password", "User One", "user1@example.com");
        List<Payment> payments = List.of(
            new Payment("payment1", 1L, user.getId(), 30000L),
            new Payment("payment2", 1L, user.getId(), 20000L)
        );
        
        // when
        Registration registration = paidSession.addRegistration(paidSession, user, payments);
        
        // then
        assertThat(registration).isNotNull();
    }
    
    @Test
    @DisplayName("모집중 상태가 아닌 경우 수강 신청 실패 테스트")
    public void addRegistrationFailsWhenNotRecruiting() {
        // given
        int maxParticipants = 20;
        Long fee = 50000L;
        List<Registration> registrations = new ArrayList<>();
        
        PaidSession paidSession = new PaidSession(maxParticipants, fee, registrations, SessionStatus.PREPARING);
        
        NsUser user = new NsUser(1L, "user1", "password", "User One", "user1@example.com");
        List<Payment> payments = List.of(new Payment("payment1", 1L, user.getId(), fee));
        
        // when & then
        assertThatThrownBy(() -> paidSession.addRegistration(paidSession, user, payments))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("강의 상태가 모집중일 때만 수강 신청이 가능합니다");
    }
    
    @Test
    @DisplayName("최대 수강 인원 초과 시 수강 신청 실패 테스트")
    public void addRegistrationFailsWhenMaxParticipantsReached() {
        // given
        int maxParticipants = 1;
        Long fee = 50000L;

        PaidSession paidSession = new PaidSession(maxParticipants, fee, List.of(new Registration()), SessionStatus.RECRUITING);
        NsUser newUser = new NsUser(2L, "user2", "password", "User Two", "user2@example.com");
        List<Payment> payments = List.of(new Payment("payment2", 1L, newUser.getId(), fee));
        
        // when & then
        assertThatThrownBy(() -> paidSession.addRegistration(paidSession, newUser, payments))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("최대 수강 인원을 초과했습니다");
    }
    
    @Test
    @DisplayName("결제 정보 없을 때 수강 신청 실패 테스트")
    public void addRegistrationFailsWhenNoPayments() {
        // given
        int maxParticipants = 20;
        Long fee = 50000L;
        List<Registration> registrations = new ArrayList<>();
        
        PaidSession paidSession = new PaidSession(maxParticipants, fee, registrations, SessionStatus.RECRUITING);
        NsUser user = new NsUser(1L, "user1", "password", "User One", "user1@example.com");
        List<Payment> emptyPayments = Collections.emptyList();
        
        // when & then
        assertThatThrownBy(() -> paidSession.addRegistration(paidSession, user, emptyPayments))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("유료 강의에 대한 결제 정보를 찾을 수 없습니다");
    }
    
    @Test
    @DisplayName("결제 금액이 일치하지 않는 경우 수강 신청 실패 테스트")
    public void addRegistrationFailsWhenPaymentAmountMismatch() {
        // given
        int maxParticipants = 20;
        Long expectedFee = 50000L;
        Long actualPayment = 40000L;
        List<Registration> registrations = new ArrayList<>();
        
        PaidSession paidSession = new PaidSession(maxParticipants, expectedFee, registrations, SessionStatus.RECRUITING);
        NsUser user = new NsUser(1L, "user1", "password", "User One", "user1@example.com");
        List<Payment> payments = List.of(new Payment("payment1", 1L, user.getId(), actualPayment));
        
        // when & then
        assertThatThrownBy(() -> paidSession.addRegistration(paidSession, user, payments))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("결제 금액이 강의 수강료와 일치하지 않습니다");
    }
} 
