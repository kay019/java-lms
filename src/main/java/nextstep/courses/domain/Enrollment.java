package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.Objects;

public class Enrollment {
  private final NsUser student;
  private final Session session;

  public Enrollment(NsUser student, Session session) {
    this.student = student;
    this.session = session;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Enrollment that = (Enrollment) o;
    return Objects.equals(student, that.student) && Objects.equals(session, that.session);
  }

  @Override
  public int hashCode() {
    return Objects.hash(student, session);
  }
}