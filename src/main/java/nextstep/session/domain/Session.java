package nextstep.session.domain;

import nextstep.courses.domain.Course;

import java.util.Objects;

public class Session {

    private Long id;

    private Course course;

    public Session(Long id) {
        this(id, new Course());
    }

    public Session(Long id, Course course) {
        this.id = id;
        this.course = course;
    }

    public void toCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(course, session.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course);
    }
}
