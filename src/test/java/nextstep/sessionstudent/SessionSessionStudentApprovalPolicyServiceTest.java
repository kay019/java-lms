package nextstep.sessionstudent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SessionSessionStudentApprovalPolicyServiceTest {
    @Mock
    private SessionStudentApprovalPolicy sessionStudentApprovalPolicy;

    @Mock
    private SessionStudentService sessionStudentService;

    @InjectMocks
    private SessionStudentApprovalService sessionStudentApprovalService;

    private NsUser loginUser;
    private long sessionId, sessionStudentId;
    private SessionStudent sessionStudent;

    @BeforeEach
    void setUp() {
        loginUser = NsUserTest.JAVAJIGI;
        sessionId = 9213819L;
        sessionStudentId = 3L;

        sessionStudent = new SessionStudent(sessionId, 29381239L, true);

        when(sessionStudentService.findById(sessionStudentId))
            .thenReturn(sessionStudent);
    }

    @Test
    void 승인_테스트() {
        SessionStudent result = sessionStudentApprovalService.approve(sessionStudentId, loginUser);
        assertThat(result.getStatus()).isEqualTo(SessionStudentStatus.APPROVED);
    }

    @Test
    void 취소_테스트() {
        SessionStudent result = sessionStudentApprovalService.cancel(sessionStudentId, loginUser);
        assertThat(result.getStatus()).isEqualTo(SessionStudentStatus.CANCELLED);
    }

}
