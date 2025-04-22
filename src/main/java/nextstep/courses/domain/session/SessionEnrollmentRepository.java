package nextstep.courses.domain.session;

public interface SessionEnrollmentRepository {
    int save(SessionEnrollment sessionEnrollment);

    SessionEnrollment findById(Long id);
}
