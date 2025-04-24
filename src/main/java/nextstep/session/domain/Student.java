package nextstep.session.domain;

import java.util.Objects;

public class Student {
    private final long id;
    private final long userId;
    private final long sessionId;
    private final String name;

    public Student(long id, long userId, long sessionId, String name) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.name = name;
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
        return userId == student.userId && sessionId == student.sessionId && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sessionId, name);
    }
}
