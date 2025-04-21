package nextstep.session.domain;

import java.util.List;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.Student;

public abstract class Session {
    private final long id;
    private final long courseId;
    private final SessionStatus status;
    private final SessionDate sessionDate;
    private final List<Student> students;

    public abstract static class Builder<T extends Builder<T>> {
        protected long id;
        protected long courseId;
        protected SessionStatus status;
        protected SessionDate sessionDate;
        protected List<Student> students;

        public T id(long id) {
            this.id = id;
            return self();
        }

        public T courseId(long courseId) {
            this.courseId = courseId;
            return self();
        }

        public T status(SessionStatus status) {
            this.status = status;
            return self();
        }

        public T sessionDate(SessionDate sessionDate) {
            this.sessionDate = sessionDate;
            return self();
        }

        public T students(List<Student> students) {
            this.students = students;
            return self();
        }

        protected abstract T self();
        public abstract Session build();
    }

    protected Session(Builder<?> builder) {
        this.id = builder.id;
        this.courseId = builder.courseId;
        this.status = builder.status;
        this.sessionDate = builder.sessionDate;
        this.students = builder.students;
    }

    public abstract void enroll(Enrollment enrollment);

    public long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public SessionDate getSessionDate() {
        return sessionDate;
    }

    public List<Student> getStudents() {
        return students;
    }

    protected boolean isDuplicateStudent(Enrollment enrollment) {
        return getStudents()
            .stream()
            .anyMatch(student -> student.equals(enrollment.getStudent()));
    }
}
