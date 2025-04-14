package nextstep.students.service;

import nextstep.students.domain.Student;
import nextstep.students.domain.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void addSessionAndSaveStudent(Student student, Long sessionId) {
        student.addSession(sessionId);
        studentRepository.save(student);
    }

    public Student findStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }
}
