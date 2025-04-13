package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaidSession extends CourseSession {

    private int maxParticipants;
    private Long fee;
    private List<Registration> registrations = new ArrayList<>();

    public PaidSession(final int maxParticipants, final Long fee, final List<Registration> registrations) {
        super();
        this.maxParticipants = maxParticipants;
        this.fee = fee;
        this.registrations = registrations;
    }

    public PaidSession(final int maxParticipants, final Long fee, final List<Registration> registrations, final SessionStatus status) {
        super(status);
        this.maxParticipants = maxParticipants;
        this.fee = fee;
        this.registrations = registrations;
    }

    @Override
    public SessionType getType() {
        return SessionType.PAID;
    }

    @Override
    public Registration addRegistration(final CourseSession session, final NsUser user, final List<Payment> payments) {
        super.validateRegistration();
        validateRegistration();
        validatePayment(payments);

        Registration registration = new Registration(session, user);
        registrations.add(registration);
        return registration;
    }

    @Override
    protected void validateRegistration() {
        if (registrations.size() >= maxParticipants) {
            throw new IllegalArgumentException("최대 수강 인원을 초과했습니다.");
        }
    }

    private void validatePayment(final List<Payment> payments) {
        if (CollectionUtils.isEmpty(payments)) {
            throw new IllegalArgumentException("유료 강의에 대한 결제 정보를 찾을 수 없습니다");
        }

        Long paidAmount = payments.stream().map(Payment::getAmount).reduce(0L, Long::sum);
        if (!paidAmount.equals(fee)) {
            throw new IllegalArgumentException("결제 금액이 강의 수강료와 일치하지 않습니다");
        }
    }
} 
