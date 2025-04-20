package nextstep.session.domain;

import java.util.ArrayList;
import java.util.List;

import nextstep.enrollment.domain.Enrollment;
import nextstep.session.image.domain.SessionCoverImage;
import nextstep.enrollment.domain.Student;

public abstract class Session {
    private final long id;
    private final long courseId;
    private final SessionCoverImage coverImage;
    private final SessionStatus status;
    private final SessionDate sessionDate;

    private final List<Student> students;

    public abstract static class Builder<T extends Builder<T>> {
        protected long id;
        protected long courseId;
        protected SessionCoverImage coverImage;
        protected SessionStatus status;
        protected SessionDate sessionDate;

        public T id(long id) {
            this.id = id;
            return self();
        }

        public T courseID(long courseId) {
            this.courseId = courseId;
            return self();
        }

        public T coverImage(SessionCoverImage coverImage) {
            this.coverImage = coverImage;
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

        protected abstract T self();
        public abstract Session build();
    }

    protected Session(Builder<?> builder) {
        this.id = builder.id;
        this.courseId = builder.courseId;
        this.coverImage = builder.coverImage;
        this.status = builder.status;
        this.sessionDate = builder.sessionDate;
        this.students = new ArrayList<>();
    }

    public abstract void enroll(Enrollment enrollment);

    public long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public SessionCoverImage getCoverImage() {
        return coverImage;
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
}
