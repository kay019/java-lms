package nextstep.courses.domain;

import java.util.List;

public interface SessionStudentRepository {
    List<Long> findSessionIdsByStudent(Long studentId);

    List<Long> findStudentIdsBySession(Long sessionId);
}
