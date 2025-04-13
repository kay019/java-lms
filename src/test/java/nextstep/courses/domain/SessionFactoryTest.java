package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionFactoryTest {

  @Test
  void 무료강의_생성() {
    Course course = new Course("자바의 정석", 1L);
    String title = "기초강의1";
    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = start.plusDays(30);

    Session session = SessionFactory.createFreeSession(course, title, start, end);

    assertEquals(MoneyType.FREE, session.moneyType());
  }

  @Test
  void 유료강의_생성() {
    Course course = new Course("자바의 정석", 1L);
    String title = "기초강의1";
    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = start.plusDays(30);
    int capacity = 10;
    long price = 10000;

    Session session = SessionFactory.createPaidSession(course, title, start, end, capacity, price);

    assertEquals(MoneyType.PAID, session.moneyType());
  }
}