package nextstep.courses.domain;

import nextstep.courses.utils.IdGenerator;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
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
  private Enrollments enrollments;

  public Session(Course course, String title, LocalDateTime startDate, LocalDateTime endDate, EnrollmentPolicy enrollmentPolicy) {
    this(IdGenerator.generate(), course, title, startDate, endDate, SessionStatus.PREPARING, enrollmentPolicy);
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status, EnrollmentPolicy enrollmentPolicy) {
    this(id, course, title, startDate, endDate, status, enrollmentPolicy, new Enrollments());
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status, EnrollmentPolicy enrollmentPolicy, List<Enrollment> enrollments) {
    this(id, course, title, startDate, endDate, status, enrollmentPolicy, new Enrollments(enrollments));
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status, EnrollmentPolicy enrollmentPolicy, Enrollments enrollments) {
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
    checkAvailability(payment);
    enrollments = new Enrollments(enrollments, new Enrollment(user, this));
  }

  private void checkAvailability(Payment payment) {
    if (!isRecruiting()) {
      throw new IllegalStateException("모집중인 상태가 아닙니다.");
    }
    enrollmentPolicy.checkEnrollAvailability(this, payment);
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

  public MoneyType moneyType() {
    return enrollmentPolicy.moneyType();
  }
}