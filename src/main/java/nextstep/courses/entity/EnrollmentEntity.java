package nextstep.courses.entity;

import nextstep.courses.domain.Enrollment;
import nextstep.users.domain.NsUser;

import java.util.Optional;

public class EnrollmentEntity {
  private final Long id;
  private final String studentId;
  private final Long sessionId;

  public EnrollmentEntity(Long id, String studentId, Long sessionId) {
    this.id = id;
    this.studentId = studentId;
    this.sessionId = sessionId;
  }

  public Long id() {
    return id;
  }
  public String studentId() {
    return studentId;
  }
  public Long sessionId() {
    return sessionId;
  }

  public static Enrollment toEnrollment(Optional<NsUser> student, Long sessionId) {
    if (student.isEmpty()) {
      throw new IllegalArgumentException("Student not found");
    }
    return new Enrollment(student.get(), sessionId);
  }
}
