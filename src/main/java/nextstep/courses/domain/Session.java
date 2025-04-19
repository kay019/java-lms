package nextstep.courses.domain;

import nextstep.courses.entity.SessionEntity;
import nextstep.courses.utils.IdGenerator;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

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

  public Session(Course course, String title, LocalDateTime startDate, LocalDateTime endDate, Image coverImage, EnrollmentPolicy enrollmentPolicy) {
    this(IdGenerator.generate(), course, title, startDate, endDate, SessionStatus.PREPARING, enrollmentPolicy, new Enrollments(), coverImage);
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status, EnrollmentPolicy enrollmentPolicy) {
    this(id, course, title, startDate, endDate, status, enrollmentPolicy, new Enrollments(), new Image("temp.jpeg"));
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

  public Enrollment enroll(NsUser user, Payment payment) {
    checkAvailability();
    checkPayment(payment);
    Enrollment enrollment = new Enrollment(user, this.id);
    enrollments.add(enrollment);
    closeIfFull();
    return enrollment;
  }

  private void closeIfFull() {
    if (enrollmentPolicy.isFull(enrollments)) {
      closeEnrollment();
    }
  }

  private void checkAvailability() {
    if (!isRecruiting()) {
      throw new IllegalStateException("모집중인 상태가 아닙니다.");
    }
    enrollmentPolicy.checkEnrollAvailability(this);
  }

  private void checkPayment(Payment payment) {
    enrollmentPolicy.checkPayment(payment);
  }

  public void openForEnrollment() {
    this.status = SessionStatus.RECRUITING;
  }

  private void closeEnrollment() {
    this.status = SessionStatus.CLOSED;
  }

  private boolean isRecruiting() {
    return this.status == SessionStatus.RECRUITING;
  }

  public int enrolledCount() {
    return enrollments.size();
  }

  public Enrollments enrollments() {
    return enrollments;
  }

  public long id() {
    return id;
  }

  public Image coverImage() {
    return coverImage;
  }

  public SessionEntity toSessionEntity() {
    return new SessionEntity(
            this.id,
            this.course.id(),
            this.coverImage.id(),
            this.title,
            this.status,
            this.enrollmentPolicy.price(),
            this.enrollmentPolicy.capacity(),
            this.enrolledCount(),
            this.startDate,
            this.endDate
    );
  }
}