package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.courses.domain.session.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

@Service("courseService")
public class CourseService {

    @Resource(name = "courseRepository")
    private CourseRepository courseRepository;

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    public void createCourse(String title, Long creatorId) {
        Course course = new Course(title, creatorId);
        courseRepository.save(course.toCourseEntity());
    }

    @Transactional
    public void deleteCourse(long courseId) throws IOException {
        Course course = Course.from(courseRepository.findById(courseId), sessionRepository.findAllByCourseId(courseId));
        course.delete();
    }
}
