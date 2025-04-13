package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class Enrollment {
  private final NsUser student;
  private final Session session;

  public Enrollment(NsUser student, Session session) {
    this.student = student;
    this.session = session;
  }
}