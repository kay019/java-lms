package nextstep.support.builder;

import nextstep.payments.domain.Payment;

public class PaymentBuilder {
    private String id = "1";
    private Long sessionId = 1L;
    private Long nsUserId = 1L;
    private Long amount = 10_000L;

    public PaymentBuilder id(String id) {
        this.id = id;
        return this;
    }

    public PaymentBuilder sessionId(Long sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public PaymentBuilder nsUserId(Long nsUserId) {
        this.nsUserId = nsUserId;
        return this;
    }

    public PaymentBuilder amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public Payment build() {
        return new Payment(id, sessionId, nsUserId, amount);
    }
}
