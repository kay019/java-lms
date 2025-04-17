package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.HashSet;
import java.util.Set;
public abstract class Session {
    protected Long id;
    protected String name;
    protected Period period;
    protected Image coverImage;
    protected SessionStatus status;
    protected Set<Long> registeredStudents = new HashSet<>();

    public Session(Long id, String name, Period period, Image coverImage, SessionStatus status) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.coverImage = coverImage;
        this.status = status;
    }

    public void register(Long studentId, Payment payment) {
        if (status != SessionStatus.RECRUITING) {
            throw new IllegalStateException("수강 신청은 모집중일 때만 가능합니다.");
        }

        validateRegistration(studentId, payment);
        registeredStudents.add(studentId);
    }

    public boolean isRegistered(Long studentId) {
        return registeredStudents.contains(studentId);
    }

    protected abstract void validateRegistration(Long studentId, Payment payment);

    public abstract SessionType getType();
}