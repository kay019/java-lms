package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

import nextstep.payments.domain.Payment;

public class FreeSession extends Session {

    public FreeSession(Long id, Image coverImage, Period period, List<Participant> participants) {
        super(id, coverImage, period, participants);
    }

    public FreeSession(Image coverImage, Period period) {
        this(null, coverImage, period, new ArrayList<>());
    }

    @Override
    protected void validateEnrollment(Payment payment) {
        // 무료 세션은 참여할 수 있음
    }
}
