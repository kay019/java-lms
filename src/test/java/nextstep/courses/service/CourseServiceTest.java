package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.entity.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void createCourse_성공() {
        String title = "Java Programming";
        Long creatorId = 1L;

        courseService.createCourse(title, creatorId);

        verify(courseRepository, times(1)).save(any(CourseEntity.class));
    }

//    @Test
//    void deleteCourse_성공() throws IOException {
//        Long courseId = 1L;
//        CourseEntity courseEntity = mock(CourseEntity.class);
//        when(courseRepository.findById(courseId)).thenReturn(courseEntity);
//        when(sessionRepository.findAllByCourseId(courseId)).thenReturn(Collections.emptyList());
//
//        try (MockedStatic<Course> mockedStaticCourse = mockStatic(Course.class)) {
//            Course courseMock = mock(Course.class);
//            mockedStaticCourse
//                .when(() -> Course.from(courseEntity, Collections.emptyList()))
//                .thenReturn(courseMock);
//
//            courseService.deleteCourse(courseId);
//
//            verify(courseMock, times(1)).delete();
//        }
//    }
}
