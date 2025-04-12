package nextstep.sessionstudent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SessionStudentApprovalServiceTest {
    @Mock
    private CourseService courseService;

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionStudentService sessionStudentService;

    @InjectMocks
    private SessionStudentApprovalService sessionStudentApprovalService;

    @Nested
    class 정상케이스 {
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

            when(courseService.findById(courseId)).thenReturn(course);
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

    @Test
    void 세션_수강신청_상태가_대기가_아니면_예외가_발생한다() {
        NsUser javajigi = NsUserTest.JAVAJIGI;
        long sessionStudentId = 23928392L;

        SessionStudent sessionStudent = new SessionStudent(1232123L, 231L, true);
        sessionStudent.approved();

        when(sessionStudentService.findById(sessionStudentId)).thenReturn(sessionStudent);

        IllegalStateException e = catchThrowableOfType(
            () -> sessionStudentApprovalService.cancel(sessionStudentId, javajigi),
            IllegalStateException.class);

        assertThat(e).hasMessage("세션 수강신청이 대기 상태가 아닙니다.");
    }

    @Test
    void 선발_가능한_세션이여야_한다() {
        NsUser javajigi = NsUserTest.JAVAJIGI;
        long sessionStudentId = 23928392L;
        long sessionId = 2139213L;

        Session session = new SessionBuilder()
            .selectionRequired(false)
            .build();
        SessionStudent sessionStudent = new SessionStudent(sessionId, 231L, true);

        when(sessionStudentService.findById(sessionStudentId)).thenReturn(sessionStudent);
        when(sessionService.findById(sessionId)).thenReturn(session);

        IllegalStateException e = catchThrowableOfType(
            () -> sessionStudentApprovalService.cancel(sessionStudentId, javajigi),
            IllegalStateException.class);

        assertThat(e).hasMessage("선발 가능한 세션이 아닙니다.");
    }

    @Test
    void course의_owner여야_승인_거절을_할_수_있다() {
        NsUser javajigi = NsUserTest.JAVAJIGI;
        long sessionId = 213213L;
        long courseId = 112323L;
        long sessionStudentId = 40301L;

        Course course = new Course("자바", 213912389L);
        Session session = new SessionBuilder()
            .courseId(courseId)
            .selectionRequired(true)
            .build();
        SessionStudent sessionStudent = new SessionStudent(sessionId, 231L, true);

        when(courseService.findById(courseId)).thenReturn(course);
        when(sessionService.findById(sessionId)).thenReturn(session);
        when(sessionStudentService.findById(sessionStudentId)).thenReturn(sessionStudent);

        IllegalStateException e = catchThrowableOfType(
            () -> sessionStudentApprovalService.approve(sessionStudentId, javajigi),
            IllegalStateException.class);

        assertThat(e).hasMessage("세션 수강신청을 승인/거절할 권한이 없습니다.");
    }

}
