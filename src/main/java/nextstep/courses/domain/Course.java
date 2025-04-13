package nextstep.courses.domain;

import nextstep.common.domain.Audit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private CourseIdentity identity;
    private String title;
    private int term;
    private Audit audit;
    private List<CourseSession> sessions = new ArrayList<>();

    public Course(final Long id, final String title, final Long creatorId, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.identity = new CourseIdentity(id, creatorId);
        this.title = title;
        this.audit = new Audit(createdAt, updatedAt);
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return identity.getCreatorId();
    }

    public LocalDateTime getCreatedAt() {
        return audit.getCreatedAt();
    }
    
    public Long getId() {
        return identity.getId();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + identity.getId() +
                ", title='" + title + '\'' +
                ", term=" + term +
                ", creatorId=" + identity.getCreatorId() +
                ", createdAt=" + audit.getCreatedAt() +
                ", updatedAt=" + audit.getUpdatedAt() +
                '}';
    }
    
    public static class CourseIdentity {
        private final Long id;
        private final Long creatorId;
        
        public CourseIdentity(Long id, Long creatorId) {
            this.id = id;
            this.creatorId = creatorId;
        }
        
        public Long getId() {
            return id;
        }
        
        public Long getCreatorId() {
            return creatorId;
        }
    }
}
