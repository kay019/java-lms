package nextstep.courses.domain;

import java.time.LocalDateTime;

public class Session {
  private Long id;
  private Course course;
  private Image coverImage;
  private String title;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private SessionStatus status;

  public Session(Course course, String title, LocalDateTime startDate, LocalDateTime endDate) {
    this(0L, course, title, startDate, endDate, SessionStatus.PREPARING);
  }

  public Session(Long id, Course course, String title, LocalDateTime startDate, LocalDateTime endDate, SessionStatus status) {
    this.id = id;
    this.course = course;
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }


}