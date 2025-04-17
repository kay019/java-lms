package nextstep.sessionstudent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionBuilder;
import nextstep.session.service.SessionService;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SessionStudentApprovalServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionStudentService sessionStudentService;

    @InjectMocks
    private SessionStudentApprovalService sessionStudentApprovalService;

    private NsUser loginUser;
    private long sessionId, courseId, sessionStudentId;
    private Course course;
    private Session session;
    private SessionStudent sessionStudent;

    @BeforeEach
    void setUp() {
        loginUser = NsUserTest.JAVAJIGI;
        sessionId = 9213819L;
        courseId = 1293082L;
        sessionStudentId = 3L;

        course = new Course("자바", loginUser.getId());
        session = new SessionBuilder()
            .courseId(courseId)
            .selectionRequired(true)
            .build();
        sessionStudent = new SessionStudent(sessionId, 29381239L, true);

        when(courseRepository.findById(courseId)).thenReturn(course);
        when(sessionService.findById(sessionId)).thenReturn(session);
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
