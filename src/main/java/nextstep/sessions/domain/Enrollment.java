package nextstep.sessions.domain;

import static nextstep.sessions.domain.SessionStatus.ONGOING;

import java.util.Objects;
import nextstep.users.domain.NsUser;

public class Enrollment {
    private final SessionStatus sessionStatus;

    private final Students students;

    public Enrollment(SessionStatus sessionStatus, Students students) {
        this.sessionStatus = sessionStatus;
        this.students = students;
    }

    public void enroll(NsUser nsUser) {
        if (!sessionStatus.isSameAs(ONGOING)) {
            throw new IllegalStateException("session is not in progress");
        }
        students.enroll(nsUser);
    }

    public String getSessionStatus() {
        return sessionStatus.getDescription();
    }

    public Long getCapacity() {
        return this.students.getCapacity();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enrollment that = (Enrollment) o;
        return sessionStatus == that.sessionStatus && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionStatus, students);
    }


}
