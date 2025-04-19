package nextstep.courses.domain;

import nextstep.courses.entity.SessionEntity;

import java.time.LocalDateTime;

public class SessionFactory {

  public static Session createFreeSession(Course course, String title, LocalDateTime start, LocalDateTime end) {
    return new Session(course, title, start, end, new FreeEnrollmentPolicy());
  }

  public static Session createPaidSession(Course course, String title, LocalDateTime start, LocalDateTime end, int capacity, long price) {
    return new Session(course, title, start, end, new PaidEnrollmentPolicy(capacity, price));
  }

  public static Session createSessionFromSessionEntity(SessionEntity sessionEntity, Course course, Image image, Enrollments enrollments) {
    EnrollmentPolicy enrollmentPolicy = sessionEntity.price() == 0
            ? new FreeEnrollmentPolicy()
            : new PaidEnrollmentPolicy(sessionEntity.maxCapacity(), sessionEntity.price());

    return new Session(sessionEntity.id(), course, sessionEntity.title(), sessionEntity.startDate(), sessionEntity.endDate(),
            sessionEntity.status(), enrollmentPolicy, enrollments, image);
  }
}
