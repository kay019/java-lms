package nextstep.courses.domain;

import java.time.LocalDateTime;

public class Course {
  private final Long id;
  private final String title;
  private final Long creatorId;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private Sessions sessions;
  private final Cohort cohort;

  public Course() {
    this(0L, null, null, LocalDateTime.now(), null, new Sessions(), new Cohort());
  }

  public Course(String title, Long creatorId) {
    this(0L, title, creatorId, LocalDateTime.now(), null, new Sessions(), new Cohort());
  }

  public Course (Long id, String title, Long createId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this(id, title, createId, createdAt, updatedAt, new Sessions(), new Cohort());
  }

  public Course(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt, Sessions sessions, Cohort cohort) {
    this.id = id;
    this.title = title;
    this.creatorId = creatorId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.sessions = sessions;
    this.cohort = cohort;
  }

  public String getTitle() {
    return title;
  }

  public Long getCreatorId() {
    return creatorId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void addSession(Session session) {
    sessions.add(session);
  }

  public Sessions getSessions() {
    return sessions;
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
