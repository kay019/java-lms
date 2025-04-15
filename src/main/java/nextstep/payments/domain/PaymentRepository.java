package nextstep.payments.domain;

public interface PaymentRepository {
    Payment findById(Long paymentId);
}
