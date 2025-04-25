package nextstep.session.domain;

import java.util.Objects;

import nextstep.session.exception.RegistrationStateException;

import static nextstep.session.domain.RegistrationStatus.APPROVED;
import static nextstep.session.domain.RegistrationStatus.CANCELED;
import static nextstep.session.domain.RegistrationStatus.PENDING;
import static nextstep.session.domain.RegistrationStatus.SELECTED;

public class Student {
    private final long id;
    private final long userId;
    private final long sessionId;
    private final String name;
    private RegistrationStatus status;

    public Student(long id, long userId, long sessionId, String name) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.name = name;
        this.status = PENDING;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    void markAsSelected() {
        if (status != PENDING) {
            throw new RegistrationStateException();
        }
        this.status = SELECTED;
    }

    void markAsApproved() {
        if (status != SELECTED) {
            throw new RegistrationStateException();
        }
        this.status = APPROVED;
    }

    void markAsCanceled() {
        if (status != PENDING) {
            throw new RegistrationStateException();
        }
        this.status = CANCELED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return userId == student.userId && sessionId == student.sessionId && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sessionId, name);
    }
}
