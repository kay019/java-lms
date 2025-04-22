package nextstep.users.domain;

public interface SelectedUserRepository {
    boolean existsByCourseIdAndUserId(Long courseId, Long userId);
}
