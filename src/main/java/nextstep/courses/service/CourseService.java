package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.payments.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

@Service("courseService")
public class CourseService {

    @Resource(name = "courseRepository")
    private CourseRepository courseRepository;

    @Resource(name = "paymentService")
    private PaymentService paymentService;

    public void createCourse(String title, Long creatorId) {
        Course course = new Course(title, creatorId);
        courseRepository.save(course);
    }

    public boolean enroll(String newPaymentId, long sessionId) throws IOException {
        return paymentService.save(newPaymentId, sessionId);
    }

    @Transactional
    public void deleteCourse(long courseId) {
        Course course = courseRepository.findById(courseId);
        course.delete();
    }
}
