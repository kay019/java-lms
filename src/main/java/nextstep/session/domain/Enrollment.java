package nextstep.session.domain;

import static nextstep.session.domain.EnrollmentStatus.ENROLLED;

import java.util.Objects;
import nextstep.users.domain.NsUser;

public class Enrollment {
    private final SessionStatus sessionStatus;
    private final EnrollmentStatus enrollmentStatus;
    private final Students students;

    public Enrollment(SessionStatus sessionStatus, EnrollmentStatus enrollmentStatus, Students students) {
        this.sessionStatus = sessionStatus;
        this.enrollmentStatus = enrollmentStatus;
        this.students = students;
    }

    public void enroll(NsUser nsUser) {
        if (!enrollmentStatus.isSameAs(ENROLLED)) {
            throw new IllegalStateException("session is not in enrollment");
        }
        students.enroll(nsUser);
    }

    public String getSessionStatus() {
        return sessionStatus.name();
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus.name();
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
