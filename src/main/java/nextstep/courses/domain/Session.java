package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CoverImage coverImage;

    private SessionType sessionType;

    private SessionStatus sessionStatus;

    private Long price;

    private Long capacity;

    private final List<EnrollmentHistory> enrollments = new ArrayList<>();


    private Session(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, CoverImage coverImage, SessionType sessionType, SessionStatus sessionStatus, Long price, Long capacity) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.coverImage = coverImage;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.price = price;
        this.capacity = capacity;
    }

    public static Session register(Long id, CoverImage coverImage, SessionType sessionType, Long sessionPrice, Long capacity) {
        if (sessionType.isPaid() && capacity == null || capacity <= 0) {
            throw new IllegalStateException("유료강의는 수강인원을 입력해야 합니다.");
        }

        if (sessionType.isFree()) {
            capacity = null;
        }

        return new Session(
                id,
                LocalDateTime.now(),
                LocalDateTime.now(),
                coverImage,
                sessionType,
                SessionStatus.READY, // 초기 상태는 항상 준비중
                sessionPrice,
                capacity
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

        if (sessionType.isPaid() && !Objects.equals(this.price, amount)) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다.");
        }

        if (isFull()) {
            throw new IllegalStateException("수강 정원을 초과했습니다");
        }

        EnrollmentHistory history = new EnrollmentHistory(user, this, enrolledAt);
        enrollments.add(history);
        return history;
    }

    public Long getId() {
        return id;
    }

    private boolean isFull() {
        return sessionType.isPaid() && enrollments.size() >= capacity;
    }
}
