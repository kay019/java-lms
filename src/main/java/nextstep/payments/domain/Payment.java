package nextstep.payments.domain;

import nextstep.courses.domain.CourseSession;

import java.time.LocalDateTime;

public class Payment {
    private String id;

    // 결제한 강의 아이디
    private CourseSession.CompositeKey sessionId;

    // 결제한 사용자 아이디
    private Long nsUserId;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String id, Long sessionId, Long courseId, Long nsUserId, Long amount) {
        this.id = id;
        this.sessionId = new CourseSession.CompositeKey(sessionId, courseId);
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public CourseSession.CompositeKey getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public Long getAmount() {
        return amount;
    }
}
