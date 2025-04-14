package nextstep.courses.domain;

import nextstep.courses.utils.IdGenerator;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.List;

public class Session {
  private final Long id;
  private final Course course;
  private final Image coverImage;
  private final String title;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private SessionStatus status;
  private final EnrollmentPolicy enrollmentPolicy;
  private Enrollments enrollments;

  public Session(Course course, String title, LocalDateTime startDate, LocalDateTime endDate, EnrollmentPolicy enrollmentPolicy) {
    this(IdGenerator.generate(), course, title, startDate, endDate, SessionStatus.PREPARING, enrollmentPolicy);
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status, EnrollmentPolicy enrollmentPolicy) {
    this(id, course, title, startDate, endDate, status, enrollmentPolicy, new Enrollments(), new Image("temp.jpeg"));
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status, EnrollmentPolicy enrollmentPolicy, List<Enrollment> enrollments) {
    this(id, course, title, startDate, endDate, status, enrollmentPolicy, new Enrollments(enrollments), new Image("temp.jpeg"));
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status, EnrollmentPolicy enrollmentPolicy, Enrollments enrollments, Image coverImage) {
    this.id = id;
    this.course = course;
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.enrollmentPolicy = enrollmentPolicy;
    this.enrollments = enrollments;
    this.coverImage = coverImage;
  }

  public void enroll(NsUser user, Payment payment) {
    checkAvailability();
    checkPayment(payment);
    enrollments = new Enrollments(enrollments, new Enrollment(user, this));
  }

  private void checkAvailability() {
    if (!isRecruiting()) {
      throw new IllegalStateException("모집중인 상태가 아닙니다.");
    }
    try {
      enrollmentPolicy.checkEnrollAvailability(this);
    } catch (IllegalStateException e) {
      closeEnrollment();
      throw e;
    }
  }

  private void checkPayment(Payment payment) {
    enrollmentPolicy.checkPayment(payment);
  }

  public void openForEnrollment() {
    if (isRecruiting()) {
      throw new IllegalStateException("이미 모집중입니다.");
    }
    if (isClosed()) {
      throw new IllegalStateException("모집이 종료된 상태입니다.");
    }
    this.status = SessionStatus.RECRUITING;
  }

  public void closeEnrollment() {
    if (isPreparing()) {
      throw new IllegalStateException("모집이 시작되지 않은 상태입니다.");
    }
    if (isClosed()) {
      throw new IllegalStateException("이미 모집이 종료되었습니다.");
    }
    this.status = SessionStatus.CLOSED;
  }

  private boolean isPreparing() {
    return this.status == SessionStatus.PREPARING;
  }

  private boolean isRecruiting() {
    return this.status == SessionStatus.RECRUITING;
  }

  private boolean isClosed() {
    return this.status == SessionStatus.CLOSED;
  }

  public int enrolledCount() {
    return enrollments.size();
  }
}