package nextstep.users.domain;

public class SelectedUser {
    private final Long courseId;
    private final Long userId;
    private final boolean selected;

    public SelectedUser(Long courseId, Long userId, boolean selected) {
        this.courseId = courseId;
        this.userId = userId;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getUserId() {
        return userId;
    }
}