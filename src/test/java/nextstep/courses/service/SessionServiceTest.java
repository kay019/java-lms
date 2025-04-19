package nextstep.courses.service;

import nextstep.courses.domain.Cohort;
import nextstep.courses.domain.Course;
import nextstep.courses.domain.EnrollmentPolicy;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.PaidEnrollmentPolicy;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionStatus;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class SessionServiceTest {
  @Autowired
  private SessionService sessionService;

  @Test
  void crud() {
    Course course = new Course(1L, "자바뿌셔", 1L, LocalDateTime.now(), LocalDateTime.now().plusDays(10), new Cohort(2L, "1기"));
    LocalDateTime startDate = LocalDateTime.of(2023, 10, 1, 0, 0);
    Image coverImage = new Image(1L, "temp.jpeg");
    EnrollmentPolicy enrollmentPolicy = new PaidEnrollmentPolicy(10, 10_000);
    Session session = new Session(course, "자바뿌셔 1강", startDate, startDate.plusDays(1), coverImage, enrollmentPolicy);

    long sessionId = sessionService.save(session);
    Session savedSession = sessionService.findById(sessionId);

    assertThat(savedSession.id()).isEqualTo(sessionId);
    assertThat(savedSession.coverImage().id()).isEqualTo(session.coverImage().id());
    assertThat(savedSession.enrolledCount()).isEqualTo(0);
    assertThat(savedSession.toSessionEntity().price()).isEqualTo(enrollmentPolicy.price());
    assertThat(savedSession.toSessionEntity().maxCapacity()).isEqualTo(enrollmentPolicy.capacity());
  }

  @Test
  void crud_Image() {
    Image image = new Image("temp.jpeg");
    long imageId = sessionService.saveImage(image);

    Image savedImage = sessionService.findImageById(imageId);

    assertThat(savedImage.id()).isEqualTo(imageId);
    assertThat(savedImage.toImageEntity().filename()).isEqualTo("temp.jpeg");
  }

  @Test
  void 수업등록_테스트() {
    Course course = new Course(1L, "자바뿌셔", 1L, LocalDateTime.now(), LocalDateTime.now().plusDays(10), new Cohort(2L, "1기"));
    LocalDateTime startDate = LocalDateTime.of(2023, 10, 1, 0, 0);
    Image coverImage = new Image(1L, "temp.jpeg");
    EnrollmentPolicy enrollmentPolicy = new PaidEnrollmentPolicy(1, 10_000);
    Session session = new Session(1L, course, "자바뿌셔 1강", startDate, startDate.plusDays(1), SessionStatus.RECRUITING, coverImage, enrollmentPolicy);
    NsUser student = new NsUser(1L, "userId", "password", "name", "email");

    sessionService.enroll(session, student, new Payment(session, student, 10_000L));

    assertThat(session.enrolledCount()).isEqualTo(1);
  }

  @Test
  void 수업등록_금액부족_테스트() {
    Course course = new Course(1L, "자바뿌셔", 1L, LocalDateTime.now(), LocalDateTime.now().plusDays(10), new Cohort(2L, "1기"));
    LocalDateTime startDate = LocalDateTime.of(2023, 10, 1, 0, 0);
    Image coverImage = new Image(1L, "temp.jpeg");
    EnrollmentPolicy enrollmentPolicy = new PaidEnrollmentPolicy(1, 10_000);
    Session session = new Session(1L, course, "자바뿌셔 1강", startDate, startDate.plusDays(1), SessionStatus.RECRUITING, coverImage, enrollmentPolicy);
    NsUser student = new NsUser(1L, "userId", "password", "name", "email");

    assertThatThrownBy(() -> sessionService.enroll(session, student, new Payment(session, student, 10L)))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("결제 금액이 일치하지 않습니다.");
  }

  @Test
  void 수업등록_정원초과() {
    Course course = new Course(1L, "자바뿌셔", 1L, LocalDateTime.now(), LocalDateTime.now().plusDays(10), new Cohort(2L, "1기"));
    LocalDateTime startDate = LocalDateTime.of(2023, 10, 1, 0, 0);
    Image coverImage = new Image(1L, "temp.jpeg");
    EnrollmentPolicy enrollmentPolicy = new PaidEnrollmentPolicy(1, 10_000);
    Session session = new Session(1L, course, "자바뿌셔 1강", startDate, startDate.plusDays(1), SessionStatus.RECRUITING, coverImage, enrollmentPolicy);
    NsUser student = new NsUser(1L, "userId", "password", "name", "email");

    sessionService.enroll(session, student, new Payment(session, student, 10_000L));

    assertThatThrownBy(() -> sessionService.enroll(session, student, new Payment(session, student, 10_000L)))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("모집중인 상태가 아닙니다.");
    assertThat(session.enrolledCount()).isEqualTo(1);
    assertThat(session.toSessionEntity().status()).isEqualTo(SessionStatus.CLOSED);
  }
}
