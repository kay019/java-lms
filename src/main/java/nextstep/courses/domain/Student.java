package nextstep.courses.domain;

import java.util.Objects;

import nextstep.users.domain.NsUser;

public class Student {
    private final long id;
    private final long userId;
    private final long sessionId;
    private final String name;

    public Student(long id, NsUser user, long sessionId) {
        this.id = id;
        this.userId = user.getId();
        this.name = user.getName();
        this.sessionId = sessionId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && userId == student.userId && sessionId == student.sessionId && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, sessionId, name);
    }
}
