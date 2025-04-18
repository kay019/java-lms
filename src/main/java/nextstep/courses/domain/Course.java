package nextstep.courses.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.Sessions;
import nextstep.courses.entity.CourseEntity;
import nextstep.courses.entity.SessionEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Course extends BaseDomain {

    private final String title;

    private final Long creatorId;

    private final Sessions sessions;

    public static Course from(CourseEntity courseEntity, List<SessionEntity> sessionEntities) throws IOException {
        Sessions sessions = new Sessions(Session.from(sessionEntities));
        return new Course(
            courseEntity.getId(),
            courseEntity.isDeleted(),
            courseEntity.getTitle(),
            courseEntity.getCreatorId(),
            sessions,
            courseEntity.getCreatedAt(),
            courseEntity.getUpdatedAt()
        );
    }

    public Course(String title, Long creatorId) {
        this(null, title, creatorId, new Sessions(), LocalDateTime.now(), null);
    }

    public Course(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id, title, creatorId, new Sessions(), createdAt, updatedAt);
    }

    public Course(Long id, String title, Long creatorId, Sessions sessions, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id, false, title, creatorId, sessions, createdAt, updatedAt);
    }

    public Course(Long id, boolean deleted, String title, Long creatorId, Sessions sessions, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, deleted, createdAt, updatedAt);
        this.title = title;
        this.creatorId = creatorId;
        this.sessions = sessions;
    }

    public void delete() {
        this.deleted = true;
        sessions.delete();
        this.updatedAt = LocalDateTime.now();
    }

    public CourseEntity toCourseEntity() {
        return CourseEntity.builder()
            .id(id)
            .title(title)
            .creatorId(creatorId)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .deleted(deleted)
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
            Objects.equals(title, course.title) &&
            Objects.equals(creatorId, course.creatorId) &&
            Objects.equals(createdAt, course.createdAt) &&
            Objects.equals(updatedAt, course.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, creatorId, createdAt, updatedAt);
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
