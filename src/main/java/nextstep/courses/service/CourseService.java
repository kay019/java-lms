package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionDescriptor;
import nextstep.courses.domain.session.Sessions;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.payments.domain.PaymentRepository;
import nextstep.payments.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("courseService")
public class CourseService {
    @Resource(name = "courseRepository")
    private CourseRepository courseRepository;

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "paymentService")
    private PaymentService paymentService;

    @Resource(name = "paymentRepository")
    private PaymentRepository paymentRepository;

    public void createCourse(String title, Long creatorId) {
        Course course = new Course(title, creatorId);
        courseRepository.save(course);
    }

    public void createSession(Long courseId, SessionConstraint constraint, SessionDescriptor descriptor) {
        Course course = courseRepository.findById(courseId);
        Session newSession = new Session(course, constraint, descriptor);
        sessionRepository.save(newSession);
    }

    public boolean enroll(String newPaymentId, long sessionId) {
        return paymentService.save(newPaymentId, sessionId);
    }

    @Transactional
    public void deleteSession(long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        session.delete();
    }

    @Transactional
    public void deleteCourse(long courseId) {
        Course course = courseRepository.findById(courseId);
        Sessions sessions = new Sessions(sessionRepository.findByCourse(courseId));
        course.delete();
        sessions.delete();
    }
}
