package nextstep.courses.service;

import nextstep.courses.domain.*;
import nextstep.courses.infrastructure.CourseSessionRepository;
import nextstep.courses.infrastructure.RegistrationRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.users.domain.NsUser;
import nextstep.users.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionRegistrationServiceTest {

    @Mock
    private CourseSessionRepository sessionRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionRegistrationService sessionRegistrationService;

    @Test
    @DisplayName("세션 등록 성공 테스트")
    public void registerForSessionSuccess() {
        // given
        Long sessionId = 1L;
        String userId = "user1";
        
        NsUser mockUser = new NsUser(1L, userId, "password", "User One", "user1@example.com");
        PaidSession mockSession = mock(PaidSession.class);
        
        List<Payment> mockPayments = Collections.singletonList(
            new Payment("payment1", sessionId, mockUser.getId(), 50000L)
        );
        
        Registration mockRegistration = new Registration(mockSession, mockUser);
        
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(mockSession));
        when(userService.findById(userId)).thenReturn(mockUser);
        when(paymentService.findAllPayment(mockSession, mockUser)).thenReturn(mockPayments);
        when(mockSession.addRegistration(mockSession, mockUser, mockPayments)).thenReturn(mockRegistration);
        when(registrationRepository.save(any(Registration.class))).thenReturn(mockRegistration);
        
        // when
        Registration result = sessionRegistrationService.registerForSession(sessionId, userId);
        
        // then
        assertThat(result).isNotNull();
        verify(sessionRepository).findById(sessionId);
        verify(userService).findById(userId);
        verify(paymentService).findAllPayment(mockSession, mockUser);
        verify(mockSession).addRegistration(mockSession, mockUser, mockPayments);
        verify(registrationRepository).save(any(Registration.class));
    }
    
    @Test
    @DisplayName("존재하지 않는 세션 등록 시 예외 발생")
    public void registerForNonExistentSessionThrowsException() {
        // given
        Long sessionId = 999L;
        String userId = "user1";
        
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        
        // when & then
        assertThatThrownBy(() -> sessionRegistrationService.registerForSession(sessionId, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("세션을 찾을 수 없습니다");
        
        verify(sessionRepository).findById(sessionId);
        verifyNoInteractions(userService);
        verifyNoInteractions(paymentService);
        verifyNoInteractions(registrationRepository);
    }
    
    @Test
    @DisplayName("존재하지 않는 사용자 등록 시 예외 발생")
    public void registerWithNonExistentUserThrowsException() {
        // given
        Long sessionId = 1L;
        String userId = "nonexistentUser";
        
        PaidSession mockSession = mock(PaidSession.class);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(mockSession));
        when(userService.findById(userId)).thenThrow(new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        
        // when & then
        assertThatThrownBy(() -> sessionRegistrationService.registerForSession(sessionId, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다");
        
        verify(sessionRepository).findById(sessionId);
        verify(userService).findById(userId);
        verifyNoInteractions(paymentService);
        verifyNoInteractions(registrationRepository);
    }
}
