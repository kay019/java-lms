package nextstep.session.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import nextstep.session.domain.cover.SessionCover;
import nextstep.session.domain.type.SessionType;
import nextstep.users.domain.NsUser;

public class Session {
    private final Long id;

    private final LocalDateTime startedAt;

    private final LocalDateTime endedAt;

    private final List<SessionCover> covers;

    private final SessionType sessionType;

    private final Enrollment enrollment;

    public Session(Long id) {
        this(id, null, null, Collections.emptyList(), null, null, null, null, null);
    }

    public Session(Long id, LocalDateTime startedAt, LocalDateTime endedAt, List<SessionCover> covers,
                   SessionType sessionType,
                   SessionStatus sessionStatus, EnrollmentStatus enrollmentStatus, Long capacity,
                   List<Student> students) {
        this.id = id;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.covers = covers;
        this.sessionType = sessionType;
        this.enrollment = new Enrollment(sessionStatus, enrollmentStatus, new Students(capacity, students));
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

    public List<SessionCover> getCovers() {
        return Collections.unmodifiableList(covers);
    }

    public String getSessionStatus() {
        return this.enrollment.getSessionStatus();
    }

    public String getEnrollmentStatus() {
        return this.enrollment.getEnrollmentStatus();
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
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
