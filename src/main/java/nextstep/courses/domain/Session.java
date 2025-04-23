package nextstep.courses.domain;

import nextstep.courses.domain.image.CoverImage;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public abstract class Session {
    protected Long id;

    protected CoverImage coverImage;

    protected SessionStatus status;

    protected SessionDate date;

    protected Students students;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    public Session(Long id, CoverImage coverImage,
                   SessionStatus status, SessionDate date) {
        this.id = id;
        this.coverImage = coverImage;
        this.status = status;
        this.date = date;
        this.students = new Students();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public Session(Long id, SessionStatus status, SessionDate date,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    } // TODO : 이미지, 학생 정보는 따로 추가

    public abstract void enroll(Payment payment);

    public int getStudentSize() {
        return students.size();
    }

    public SessionStatus getStatus() {
        return status;
    }

    public SessionDate getDate() {
        return date;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void attachCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }
}
