package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("courseService")
public class CourseService {
    @Resource(name = "courseRepository")
    private CourseRepository courseRepository;

    public void createCourse(String title, Long creatorId) {
        Course course = new Course(title, creatorId);
        courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(long id) {
        Course course = courseRepository.findById(id);
        course.delete();
    }
}
