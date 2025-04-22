package nextstep.users.domain;

import nextstep.courses.domain.ApplicationState;

import java.util.Objects;

public class NsStudent {
    private Long userId;
    private final Long registeredSessionId;
    private ApplicationState applicationState;

    public NsStudent(NsUser user, Long sessionId) {
        this(user.getId(), sessionId, ApplicationState.PENDING);
    }

    public NsStudent(Long userId, Long sessionId) {
        this(userId, sessionId, ApplicationState.PENDING);
    }

    public NsStudent(Long userId, Long sessionId, ApplicationState applicationState) {
        this.userId = userId;
        this.registeredSessionId = sessionId;
        this.applicationState = applicationState;
    }

    public boolean isSameUser(NsUser user) {
        return this.userId == user.getId();
    }

    public Long getUserId() {
        return userId;
    }

    public void changeStatus(ApplicationState applicationState) {
        if (!ApplicationState.isPending(applicationState)) {
            throw new IllegalArgumentException("이미 수강 상태가 결정된 학생입니다.");
        }
        this.applicationState = applicationState;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NsStudent student = (NsStudent) o;
        return Objects.equals(userId, student.userId) && Objects.equals(registeredSessionId, student.registeredSessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, registeredSessionId);
    }
}
