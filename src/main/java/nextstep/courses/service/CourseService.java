package nextstep.courses.service;

import java.time.LocalDateTime;
import java.util.List;
import nextstep.courses.domain.Course;
import nextstep.sessions.domain.Session;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    public void createCourse(long id, String title, long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt,
                             List<Session> sessions) {
        Course course = new Course(id, title, creatorId, createdAt, updatedAt, sessions);
        // insert db
    }
}
