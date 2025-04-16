package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionDescriptor;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sessionService")
public class SessionService {

    @Resource(name = "courseRepository")
    private CourseRepository courseRepository;

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    public void createSession(Long courseId, SessionConstraint constraint, SessionDescriptor descriptor) {
        Course course = courseRepository.findById(courseId);
        Session newSession = new Session(course, constraint, descriptor);
        sessionRepository.save(newSession);
    }

    @Transactional
    public void deleteSession(long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        session.delete();
    }
}
