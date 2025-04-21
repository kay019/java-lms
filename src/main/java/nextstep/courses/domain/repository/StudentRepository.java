package nextstep.courses.domain.repository;

import nextstep.courses.domain.Student;

import java.util.List;

public interface StudentRepository {
    int save(Long userId, Long sessionId);

    List<Student> findAllBySessionId(Long sessionId);
}
