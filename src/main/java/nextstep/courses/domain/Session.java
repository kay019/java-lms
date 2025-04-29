package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public abstract class Session {
    protected Long id;

    protected SessionStatus sessionStatus;

    protected EnrollStatus enrollStatus;

    protected SessionDate date;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    public Session(Long id, SessionStatus sessionStatus,
                   EnrollStatus enrollStatus, SessionDate date) {
        this.id = id;
        this.sessionStatus = sessionStatus;
        this.enrollStatus = enrollStatus;
        this.date = date;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public Session(Long id, SessionStatus sessionStatus, EnrollStatus enrollStatus,
                   SessionDate date, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sessionStatus = sessionStatus;
        this.enrollStatus = enrollStatus;
        this.date = date;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public abstract Enrollment requestEnroll(int approvedStudent, Payment payment);

    public Long getId() {
        return id;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public EnrollStatus getEnrollStatus() {
        return enrollStatus;
    }

    public SessionDate getDate() {
        return date;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
