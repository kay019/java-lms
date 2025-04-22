package nextstep.courses.domain;

import nextstep.courses.entity.CourseEntity;

import java.time.LocalDateTime;

public class Course {
  private final Long id;
  private final String title;
  private final Long creatorId;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final Cohort cohort;

  public Course() {
    this(0L, null, null, LocalDateTime.now(), null, new Cohort());
  }

  public Course(String title, Long creatorId) {
    this(0L, title, creatorId, LocalDateTime.now(), LocalDateTime.now(), new Cohort());
  }

  public Course(String title, Long creatorId, Cohort cohort) {
    this(0L, title, creatorId, LocalDateTime.now(), LocalDateTime.now(), cohort);
  }

  public Course (Long id, String title, Long createId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this(id, title, createId, createdAt, updatedAt, new Cohort());
  }

  public Course(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt, Cohort cohort) {
    this.id = id;
    this.title = title;
    this.creatorId = creatorId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.cohort = cohort;
  }

  public Long id() {
    return id;
  }

  public Cohort cohort() {
    return cohort;
  }

  public CourseEntity toCourseEntity() {
    return new CourseEntity(id, title, creatorId, cohort.id(), createdAt, updatedAt);
  }

  @Override
  public String toString() {
    return "Course{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", creatorId=" + creatorId +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
  }
}
