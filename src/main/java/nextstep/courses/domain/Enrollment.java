package nextstep.courses.domain;

import nextstep.courses.entity.EnrollmentEntity;
import nextstep.courses.utils.IdGenerator;
import nextstep.users.domain.NsUser;

import java.util.Objects;

public class Enrollment {
  private final Long id;
  private final NsUser student;
  private final Long sessionId;

  public Enrollment(NsUser student, Long sessionId) {
    this(IdGenerator.generate(), student, sessionId);
  }

  public Enrollment(Long id, NsUser student, Long sessionId) {
    this.id = id;
    this.student = student;
    this.sessionId = sessionId;
  }

  public EnrollmentEntity toEnrollmentEntity(Long sessionId) {
    return new EnrollmentEntity(this.id, this.student.userId(), sessionId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Enrollment that = (Enrollment) o;
    return Objects.equals(student, that.student) && Objects.equals(sessionId, that.sessionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(student, sessionId);
  }
}