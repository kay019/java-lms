package nextstep.students.domain;

public interface StudentRepository {
    int save(Student student);
    Student findById(Long id);
}
