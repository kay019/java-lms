package nextstep.session.domain.payment;

public interface CapacityValidator {
    boolean canEnroll(SessionPayments sessionPayments);
}
