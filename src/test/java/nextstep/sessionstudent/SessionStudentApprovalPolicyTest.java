package nextstep.sessionstudent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nextstep.courses.domain.Course;
import nextstep.courses.service.CourseService;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionBuilder;
import nextstep.session.service.SessionService;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchIllegalStateException;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionStudentApprovalPolicyTest {

    @Mock
    private CourseService courseService;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private SessionStudentApprovalPolicy sessionStudentApprovalPolicy;

    @Test
    void 선발_가능한_세션이_아니면_예외() {
        NsUser javajigi = NsUserTest.JAVAJIGI;
        Course course = new Course("Java", 1L);
        Session session = new SessionBuilder().selectionRequired(false).build();
        SessionStudent sessionStudent = new SessionStudent(1L, 1L, false);

        when(sessionService.findById(sessionStudent.getSessionId())).thenReturn(session);
        when(courseService.findById(session.getCourseId())).thenReturn(course);

        IllegalStateException e = catchIllegalStateException(() -> {
            sessionStudentApprovalPolicy.validate(sessionStudent, javajigi);
        });

        assertThat(e).hasMessage("선발 가능한 세션이 아닙니다.");
    }

    @Test
    void Course_Owner가_아니면_예외() {
        NsUser javajigi = NsUserTest.JAVAJIGI;
        Course course = new Course("Java", 2L);
        Session session = new SessionBuilder().selectionRequired(true).build();
        SessionStudent sessionStudent = new SessionStudent(1L, 1L, false);

        when(sessionService.findById(sessionStudent.getSessionId())).thenReturn(session);
        when(courseService.findById(session.getCourseId())).thenReturn(course);

        IllegalStateException e = catchIllegalStateException(() -> {
            sessionStudentApprovalPolicy.validate(sessionStudent, javajigi);
        });

        assertThat(e).hasMessage("세션 학생은 코스 소유자만 승인할 수 있습니다.");
    }

}
