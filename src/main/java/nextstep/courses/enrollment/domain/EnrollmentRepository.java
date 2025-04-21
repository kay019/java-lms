package nextstep.courses.enrollment.domain;

public interface EnrollmentRepository {
    int save(Enrollment enrollment);

    Enrollment findById(Long id);
}
