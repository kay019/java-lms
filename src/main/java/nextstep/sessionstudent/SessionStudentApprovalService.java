package nextstep.sessionstudent;

import org.springframework.stereotype.Service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.users.domain.NsUser;

@Service
public class SessionStudentApprovalService {

    private final CourseRepository courseRepository;
    private final SessionRepository sessionRepository;
    private final SessionStudentRepository sessionStudentRepository;

    public SessionStudentApprovalService(
        CourseRepository courseRepository,
        SessionRepository sessionRepository,
        SessionStudentRepository sessionStudentRepository) {
        this.courseRepository = courseRepository;
        this.sessionRepository = sessionRepository;
        this.sessionStudentRepository = sessionStudentRepository;
    }

    public SessionStudent approve(long sessionStudentId, NsUser nsUser) {
        ApprovalContext context = createApprovalContext(sessionStudentId, nsUser);
        SessionStudent sessionStudent = sessionStudentRepository.findById(sessionStudentId);
        sessionStudent.approved(context);
        return sessionStudent;
    }

    public SessionStudent cancel(long sessionStudentId, NsUser nsUser) {
        ApprovalContext context = createApprovalContext(sessionStudentId, nsUser);
        SessionStudent sessionStudent = sessionStudentRepository.findById(sessionStudentId);
        sessionStudent.cancelled(context);
        return sessionStudent;
    }

    private ApprovalContext createApprovalContext(long sessionStudentId, NsUser creator) {
        SessionStudent sessionStudent = sessionStudentRepository.findById(sessionStudentId);
        Session session = sessionRepository.findById(sessionStudent.getSessionId());
        Course course = courseRepository.findById(session.getCourseId());
        return new ApprovalContext(course, session, creator);
    }

}
