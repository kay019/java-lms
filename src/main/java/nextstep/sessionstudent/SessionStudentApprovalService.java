package nextstep.sessionstudent;

import org.springframework.stereotype.Service;

import nextstep.courses.domain.Course;
import nextstep.courses.service.CourseService;
import nextstep.session.domain.Session;
import nextstep.session.service.SessionService;
import nextstep.users.domain.NsUser;

@Service
public class SessionStudentApprovalService {

    private final CourseService courseService;
    private final SessionService sessionService;
    private final SessionStudentService sessionStudentService;

    public SessionStudentApprovalService(
        CourseService courseService,
        SessionService sessionService,
        SessionStudentService sessionStudentService) {
        this.courseService = courseService;
        this.sessionService = sessionService;
        this.sessionStudentService = sessionStudentService;
    }

    public SessionStudent approve(long sessionStudentId, NsUser nsUser) {
        validate(sessionStudentId, nsUser);
        SessionStudent sessionStudent = sessionStudentService.findById(sessionStudentId);
        sessionStudent.approved();
        return sessionStudent;
    }

    public SessionStudent cancel(long sessionStudentId, NsUser nsUser) {
        validate(sessionStudentId, nsUser);
        SessionStudent sessionStudent = sessionStudentService.findById(sessionStudentId);
        sessionStudent.cancelled();
        return sessionStudent;
    }

    private void validate(long sessionStudentId, NsUser creator) {
        SessionStudent sessionStudent = sessionStudentService.findById(sessionStudentId);

        if (sessionStudent == null) {
            throw new IllegalStateException("세션 수강신청이 존재하지 않습니다.");
        }

        if (!sessionStudent.isPending()) {
            throw new IllegalStateException("세션 수강신청이 대기 상태가 아닙니다.");
        }

        Session session = sessionService.findById(sessionStudent.getSessionId());

        if (!session.isSelectionRequired()) {
            throw new IllegalStateException("선발 가능한 세션이 아닙니다.");
        }

        Course course = courseService.findById(session.getCourseId());

        if (!course.isOwner(creator)) {
            throw new IllegalStateException("세션 수강신청을 승인/거절할 권한이 없습니다.");
        }
    }

}
