package nextstep.courses.entity;

import nextstep.courses.domain.SessionStatus;

import java.time.LocalDateTime;

public class SessionEntity {

  private final Long id;
  private final Long courseId;
  private final Long coverImageId;
  private final String title;
  private final SessionStatus status;
  private final long price;
  private final int maxCapacity;
  private final int enrolledCnt;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;

  public SessionEntity(Long id, Long courseId, Long coverImageId, String title, SessionStatus status, long price, int maxCapacity, int enrolledCnt, LocalDateTime startDate, LocalDateTime endDate) {
    this.id = id;
    this.courseId = courseId;
    this.coverImageId = coverImageId;
    this.title = title;
    this.status = status;
    this.price = price;
    this.maxCapacity = maxCapacity;
    this.enrolledCnt = enrolledCnt;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Long id() {
    return id;
  }
  public Long courseId() {
    return courseId;
  }
  public Long coverImageId() {
    return coverImageId;
  }
  public String title() {
    return title;
  }
  public SessionStatus status() {
    return status;
  }
  public long price() {
    return price;
  }
  public int maxCapacity() {
    return maxCapacity;
  }
  public int enrolledCnt() {
    return enrolledCnt;
  }
  public LocalDateTime startDate() {
    return startDate;
  }
  public LocalDateTime endDate() {
    return endDate;
  }

}
