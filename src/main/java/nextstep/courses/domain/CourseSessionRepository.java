package nextstep.courses.domain;

import java.util.List;

public interface CourseSessionRepository {
    List<Long> findCourseIdsBySession(Long sessionId);

    List<Long> findSessionIdsByCourse(Long courseId);
}
