package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CoverImage coverImage;

    private SessionType sessionType;

    private SessionStatus sessionStatus;

    private Long sessionPrice;

    private Session(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, CoverImage coverImage, SessionType sessionType, SessionStatus sessionStatus, Long sessionPrice) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.coverImage = coverImage;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.sessionPrice = sessionPrice;
    }

    public static Session register(Long id, CoverImage coverImage, SessionType sessionType, Long sessionPrice) {
        return new Session(
                id,
                LocalDateTime.now(),
                LocalDateTime.now(),
                coverImage,
                sessionType,
                SessionStatus.READY, // 초기 상태는 항상 준비중
                sessionPrice
        );
    }

    public void close() {
        this.sessionStatus = SessionStatus.CLOSED;
    }

    public void open() {
        this.sessionStatus = SessionStatus.OPEN;
    }

    public EnrollmentHistory enroll(NsUser user, Long amount, LocalDateTime enrolledAt) {
        if (sessionStatus.isNotOpen()) {
            throw new IllegalStateException("모집 중이 아닙니다");
        }

        if (sessionType.isPaid() && !Objects.equals(this.sessionPrice, amount)) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다.");
        }

        return new EnrollmentHistory(user, this, enrolledAt);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public Long getSessionPrice() {
        return sessionPrice;
    }
}
