package nextstep.courses.domain.repository;

import nextstep.courses.domain.Student;

import java.util.List;

public interface StudentRepository {
    Long save(Long userId, Long sessionId);

    List<Student> findAllBySessionId(Long sessionId);
}
