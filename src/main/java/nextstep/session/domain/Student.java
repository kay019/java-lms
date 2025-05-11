package nextstep.session.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import nextstep.users.domain.NsUser;

public class Student {
    private final Long id;
    private final NsUser user;
    private final Session session;
    private final Boolean approved;
    private final LocalDateTime created_dt;

    public Student(NsUser user, Session session) {
        this(null, user, session, false, null);
    }

    public Student(Long id, NsUser user, Session session, Boolean approved, LocalDateTime created_dt) {
        this.id = id;
        this.user = user;
        this.session = session;
        this.approved = approved;
        this.created_dt = created_dt;
    }

    public boolean isSameUser(NsUser user) {
        return this.user.matchUser(user);
    }

    public Student approve() {
        return new Student(id, user, session, true, created_dt);
    }

    public Student disApprove() {
        return new Student(id, user, session, false, created_dt);
    }

    public Long getUserId() {
        return user.getId();
    }

    public Long getSessionId() {
        return session.getId();
    }

    public Boolean getApproved() {
        return approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
