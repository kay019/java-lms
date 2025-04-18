package nextstep.courses.domain;

import java.util.Objects;

import nextstep.users.domain.NsUser;

public class Student {
    private final long id;
    private final String name;

    public Student(NsUser user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
