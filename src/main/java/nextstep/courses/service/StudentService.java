package nextstep.courses.service;

import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student findStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 학생입니다 : " + studentId)
        );
    }
}
