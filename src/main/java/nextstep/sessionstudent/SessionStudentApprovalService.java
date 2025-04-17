package nextstep.sessionstudent;

import org.springframework.stereotype.Service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.session.domain.Session;
import nextstep.session.service.SessionService;
import nextstep.users.domain.NsUser;

@Service
public class SessionStudentApprovalService {

    private final CourseRepository courseRepository;
    private final SessionService sessionService;
    private final SessionStudentService sessionStudentService;

    public SessionStudentApprovalService(
        CourseRepository courseRepository,
        SessionService sessionService,
        SessionStudentService sessionStudentService) {
        this.courseRepository = courseRepository;
        this.sessionService = sessionService;
        this.sessionStudentService = sessionStudentService;
    }

    public SessionStudent approve(long sessionStudentId, NsUser nsUser) {
        ApprovalContext context = createApprovalContext(sessionStudentId, nsUser);
        SessionStudent sessionStudent = sessionStudentService.findById(sessionStudentId);
        sessionStudent.approved(context);
        return sessionStudent;
    }

    public SessionStudent cancel(long sessionStudentId, NsUser nsUser) {
        ApprovalContext context = createApprovalContext(sessionStudentId, nsUser);
        SessionStudent sessionStudent = sessionStudentService.findById(sessionStudentId);
        sessionStudent.cancelled(context);
        return sessionStudent;
    }

    private ApprovalContext createApprovalContext(long sessionStudentId, NsUser creator) {
        SessionStudent sessionStudent = sessionStudentService.findById(sessionStudentId);
        Session session = sessionService.findById(sessionStudent.getSessionId());
        Course course = courseRepository.findById(session.getCourseId());
        return new ApprovalContext(course, session, creator);
    }

}
