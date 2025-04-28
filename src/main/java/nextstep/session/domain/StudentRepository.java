package nextstep.session.domain;

import java.util.Optional;

public interface StudentRepository {
    Long save(Student student);

    Optional<Student> findById(Long studentId);
}
