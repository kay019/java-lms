package nextstep.courses.session.service;

import nextstep.courses.course.domain.Course;
import nextstep.courses.course.domain.CourseRepository;
import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.courses.session.domain.Session;
import nextstep.courses.session.domain.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.SelectedUserRepository;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SessionService {
    @Resource(name = "courseRepository")
    private CourseRepository courseRepository;
    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "selectedUserRepository")
    private SelectedUserRepository selectedUserRepository;
    public Enrollment enrollSession(Long courseId, Long sessionId, String userId, Payment payment) {
        Course course = courseRepository.findById(courseId);
        Session session = sessionRepository.findById(sessionId);
        NsUser user = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException(userId + " : 해당 유저가 존재하지 않습니다."));

        if (course.getRequiresSelection()) {
            if (!selectedUserRepository.existsByCourseIdAndUserId(courseId, Long.parseLong(userId))) {
                throw new IllegalArgumentException("선발되지 않은 유저입니다.");
            }
            return session.enrollToSelectedCourse(payment, user);
        }

        // 일반 강의는 바로 수강 가능
        return session.enrollToGeneralCourse(payment, user);
    }
}
