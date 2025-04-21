package nextstep.enrollment.mapper;

import nextstep.enrollment.domain.Student;
import nextstep.enrollment.entity.StudentEntity;

public class StudentMapper {
    public StudentEntity toEntity(Student student) {
        return new StudentEntity(student.getId(),
            student.getUserId(),
            student.getSessionId(),
            student.getName());
    }

    public Student toDomain(StudentEntity entity) {
        return new Student(
            entity.getId(),
            entity.getUserId(),
            entity.getSessionId(),
            entity.getName()
        );
    }
}
