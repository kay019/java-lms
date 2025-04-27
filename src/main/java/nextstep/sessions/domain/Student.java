package nextstep.sessions.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import nextstep.users.domain.NsUser;

public class Student {
    private final Long id;
    private final NsUser user;  // ID 대신 실제 객체 참조
    private final Session session;  // ID 대신 실제 객체 참조
    private final LocalDateTime created_dt;

    public Student(NsUser user, Session session) {
        this(null, user, session, null);
    }

    public Student(Long id, NsUser user, Session session, LocalDateTime created_dt) {
        this.id = id;
        this.user = user;
        this.session = session;
        this.created_dt = created_dt;
    }


    public boolean isSameUser(NsUser user) {
        return this.user.matchUser(user);
    }

    public Long getUserId() {
        return user.getId();
    }

    public Long getSessionId() {
        return session.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(user, student.user)
                && Objects.equals(session, student.session) && Objects.equals(created_dt,
                student.created_dt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, session, created_dt);
    }
}
