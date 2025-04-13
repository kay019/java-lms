package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
  private final Long id;
  private final Course course;
  private Image coverImage;
  private String title;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private SessionStatus status;
  private final EnrollmentPolicy enrollmentPolicy;
  private List<Enrollment> enrollments;

  public Session(Course course, String title, LocalDateTime startDate, LocalDateTime endDate, EnrollmentPolicy enrollmentPolicy) {
    this(0L, course, title, startDate, endDate, SessionStatus.PREPARING, enrollmentPolicy);
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status, EnrollmentPolicy enrollmentPolicy) {
    this(id, course, title, startDate, endDate, status, enrollmentPolicy, new ArrayList<>());
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status, EnrollmentPolicy enrollmentPolicy, List<Enrollment> enrollments) {
    this.id = id;
    this.course = course;
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.enrollmentPolicy = enrollmentPolicy;
    this.enrollments = enrollments;
  }

  public void enroll(NsUser user, Payment payment) {
    validateEnrollment(user, payment);
    enrollments.add(new Enrollment(user, this));
  }

  private void validateEnrollment(NsUser user, Payment payment) {
    if (!isRecruiting()) {
      throw new IllegalStateException("모집중인 상태가 아닙니다.");
    }
    enrollmentPolicy.checkEnrollAvailability(this, user, payment);
  }

  private boolean isRecruiting() {
    return this.status == SessionStatus.RECRUITING;
  }

  public int enrolledCount() {
    return enrollments.size();
  }

  public void changeStatus(SessionStatus status) {
    this.status = status;
  }
}