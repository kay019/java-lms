package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionFactory {

  public static Session createFreeSession(Course course, String title, LocalDateTime start, LocalDateTime end) {
    return new Session(course, title, start, end, new FreeEnrollmentPolicy());
  }

  public static Session createPaidSession(Course course, String title, LocalDateTime start, LocalDateTime end, int capacity, long price) {
    return new Session(course, title, start, end, new PaidEnrollmentPolicy(capacity, price));
  }
}
