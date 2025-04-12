package nextstep.session.service;

import nextstep.session.domain.RecruitmentStatus;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionBuilder;
import nextstep.sessionstudent.SessionStudent;
import nextstep.sessionstudent.SessionStudentService;
import nextstep.sessionstudent.SessionStudentStatus;
import nextstep.sessionstudent.SessionStudents;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionStudentService sessionStudentService;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void registerSessionTest() {
        long sessionId = 1L;
        long amount = 5000;
        NsUser javajigi = NsUserTest.JAVAJIGI;

        Session session = new SessionBuilder()
            .id(sessionId)
            .paid(amount, 10)
            .recruitmentStatus(RecruitmentStatus.RECRUITING)
            .selectionRequired(false)
            .build();

        when(sessionService.findById(sessionId)).thenReturn(session);
        when(sessionStudentService.findBySessionId(sessionId)).thenReturn(new SessionStudents());

        SessionStudent sessionStudent
            = registrationService.registerSession(sessionId, javajigi, amount);

        assertThat(sessionStudent.getSessionId()).isEqualTo(sessionId);
        assertThat(sessionStudent.getNsUserId()).isEqualTo(javajigi.getId());
        assertThat(sessionStudent.getStatus()).isEqualTo(SessionStudentStatus.APPROVED);
    }

}
