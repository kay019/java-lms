package nextstep.payments.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PaymentEntity {
    private Long id;

    private Long userId;

    private Long sessionId;

    private Long amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean deleted;
}
