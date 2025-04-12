package nextstep.courses.domain;

import java.time.LocalDateTime;

public class Course {
    private Long id;
    private final String title;
    private final long creatorId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Course(String title, long creatorId) {
        this(null, title, creatorId, LocalDateTime.now(), null);
    }

    public Course(Long id, String title, long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
