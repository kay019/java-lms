package nextstep.courses.entity;

import nextstep.courses.domain.Cohort;
import nextstep.courses.domain.Course;
import nextstep.courses.domain.Sessions;

import java.time.LocalDateTime;

public class CourseEntity {
  private Long id;
  private String title;
  private Long creatorId;
  private Long cohortId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public CourseEntity(Long id, String title, Long creatorId, Long cohortId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.title = title;
    this.creatorId = creatorId;
    this.cohortId = cohortId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long id() {
    return id;
  }
  public String title() {
    return title;
  }
  public Long creatorId() {
    return creatorId;
  }
  public Long cohortId() {
    return cohortId;
  }
  public LocalDateTime createdAt() {
    return createdAt;
  }
  public LocalDateTime updatedAt() {
    return updatedAt;
  }

  public Course toCourse(Cohort cohort) {
    return new Course(id, title, creatorId, createdAt, updatedAt, cohort);
  }
}
