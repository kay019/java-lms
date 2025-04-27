package nextstep.session.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import nextstep.session.domain.cover.SessionCover;
import nextstep.session.domain.type.SessionType;
import nextstep.users.domain.NsUser;

public class Session {
    private final Long id;

    private final LocalDateTime startedAt;

    private final LocalDateTime endedAt;

    private final SessionCover cover;

    private final SessionType sessionType;

    private final Enrollment enrollment;

    public Session(Long id) {
        this(id, null, null, null, null, null, null, null);
    }

    public Session(Long id, LocalDateTime startedAt, LocalDateTime endedAt, SessionCover cover, SessionType sessionType,
                   SessionStatus sessionStatus, Long capacity, List<Student> students) {
        this.id = id;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.cover = cover;
        this.sessionType = sessionType;
        this.enrollment = new Enrollment(sessionStatus, new Students(capacity, students));
    }

    public Student enroll(NsUser nsUser) {
        enrollment.enroll(nsUser);
        return new Student(nsUser, this);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public String getSessionStatus() {
        return this.enrollment.getSessionStatus();
    }

    public Long getCapacity() {
        return this.enrollment.getCapacity();
    }

    public Long getPrice() {
        return this.sessionType.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(startedAt, session.startedAt)
                && Objects.equals(endedAt, session.endedAt) && Objects.equals(cover, session.cover)
                && Objects.equals(sessionType, session.sessionType) && Objects.equals(enrollment,
                session.enrollment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startedAt, endedAt, cover, sessionType, enrollment);
    }
}
