package nextstep.sessionstudent;

import org.springframework.stereotype.Service;

import nextstep.courses.domain.Course;
import nextstep.courses.service.CourseService;
import nextstep.session.domain.Session;
import nextstep.session.service.SessionService;
import nextstep.users.domain.NsUser;

@Service
public class SessionStudentApprovalPolicy {

    private final CourseService courseService;
    private final SessionService sessionService;

    public SessionStudentApprovalPolicy(
        CourseService courseService,
        SessionService sessionService) {
        this.courseService = courseService;
        this.sessionService = sessionService;
    }

    public void validate(SessionStudent sessionStudent, NsUser nsUser) {
        Course course = courseService.findById(sessionStudent.getSessionId());
        Session session = sessionService.findById(sessionStudent.getSessionId());

        if (!session.isSelectionRequired()) {
            throw new IllegalStateException("선발 가능한 세션이 아닙니다.");
        }

        if (!course.isOwner(nsUser)) {
            throw new IllegalStateException("세션 학생은 코스 소유자만 승인할 수 있습니다.");
        }
    }


}
