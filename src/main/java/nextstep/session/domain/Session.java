package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.session.domain.image.SessionCoverImage;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {

    private Long id;

    private Course course;

    private SessionCoverImage coverSessionCoverImage;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Session(Long id, Course course, SessionCoverImage sessionCoverImage, LocalDateTime startDate, LocalDateTime endDate) {
        this(id, course, sessionCoverImage, startDate, endDate, LocalDateTime.now(), LocalDateTime.now());
    }

    public Session(Long id, Course course, SessionCoverImage sessionCoverImage, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.course = course;
        this.coverSessionCoverImage = sessionCoverImage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
