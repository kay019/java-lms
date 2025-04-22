package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

import nextstep.payments.domain.Payment;

public class PaidSession extends Session {

    private final Integer maxParticipants;

    private final Long price;

    public PaidSession(Long id, Image coverImage, Period period, List<Participant> participants, SessionStatus status, Integer maxParticipants, Long price) {
        super(id, coverImage, period, participants, status);
        validate(maxParticipants, price);

        this.maxParticipants = maxParticipants;
        this.price = price;
    }

    public PaidSession(Image coverImage, Period period, Integer maxParticipants, Long price) {
       this(null, coverImage, period, new ArrayList<>(), SessionStatus.PREPARING, maxParticipants, price);
    }

    private void validate(Integer maxParticipants, Long price) {
        if (maxParticipants == null || price == null) {
            throw new IllegalArgumentException("유료 세션은 최대 참여자 수와 가격을 설정해야 합니다.");
        }

        if (maxParticipants != null && maxParticipants <= 0) {
            throw new IllegalArgumentException("최대 참여자 수는 1 이상이어야 합니다.");
        }

        if (price != null && price <= 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
    }

    @Override
    protected void validateEnrollment(Payment payment) {
        if (getParticipantsCount() >= maxParticipants) {
            throw new IllegalArgumentException("최대 참여자 수를 초과했습니다.");
        }
        
        if (!payment.isSameAmount(price)) {
            throw new IllegalArgumentException("가격이 일치하지 않습니다.");
        }
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public Long getPrice() {
        return price;
    }
}
