package nextstep.courses.course.domain;

import nextstep.courses.session.domain.Sessions;

import java.time.LocalDateTime;

public class Course {
    private Long id;

    private String title;

    private Long creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Sessions sessions;
    private Boolean requiresSelection;


    public Course() {
    }

    public static Course general(String title, Long creatorId) {
        return new Course(0L, title, creatorId, LocalDateTime.now(), null, null, false);
    }

    public static Course selective(String title, Long creatorId) {
        return new Course(0L, title, creatorId, LocalDateTime.now(), null, null, true);
    }

    public Course(String title, Long creatorId) {
        this(0L, title, creatorId, LocalDateTime.now(), null, null, false);
    }

    public Course(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt, Sessions sessions, boolean requiresSelection) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sessions = sessions;
        this.requiresSelection = requiresSelection;
    }

    public boolean canEnrollRequireSelection(boolean isSelectedUser) {
        if (!isSelectedUser) {
            throw new IllegalArgumentException("선발되지 않은 유저는 해당 코스를 수강할 수 없습니다.");
        }
        return requiresSelection;
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

    public Boolean getRequiresSelection() {
        return requiresSelection;
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
