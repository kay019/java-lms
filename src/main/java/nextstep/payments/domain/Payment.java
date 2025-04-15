package nextstep.payments.domain;

import java.time.LocalDateTime;

public class Payment {
    private String id;

    // 결제한 강의 아이디
    private Long sessionId;

    // 결제한 사용자 아이디
    private Long nsUserId;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String id, Long sessionId, Long nsUserId, Long amount) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public void isValidatePayment(Long sessionId, Long nsUserId, Long amount) {
        if (this.sessionId != sessionId) {
            throw new IllegalArgumentException("결제한 강의와 일치하지 않습니다.");
        }
        if (this.nsUserId != nsUserId) {
            throw new IllegalArgumentException("결제한 사용자와 일치하지 않습니다.");
        }
        if (this.amount != amount) {
            throw new IllegalArgumentException("결제 금액과 일치하지 않습니다.");
        }
    }
}
