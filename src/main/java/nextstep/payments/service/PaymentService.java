package nextstep.payments.service;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.payments.domain.Payments;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service("paymentService")
public class PaymentService {

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "paymentRepository")
    private PaymentRepository paymentRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public Payment payment(String id) {
        // PG사 API를 통해 id에 해당하는 결제 정보를 반환
        return new Payment();
    }

    public boolean save(String newPaymentId, long sessionId) throws IOException {
        Payment newPayment = payment(newPaymentId);
        Session session = Session.from(sessionRepository.findById(sessionId));
        Payments payments = new Payments(paymentRepository.findBySession(sessionId).stream()
            .map(paymentEntity -> {
                NsUser user = userRepository.findByUserId(paymentEntity.getUserId().toString())
                    .orElseThrow(() -> new NoSuchElementException("id에 해당하는 유저가 없습니다: " + paymentEntity.getUserId()));
                return Payment.from(paymentEntity, session, user);
            }).collect(Collectors.toList()));

        if (payments.canEnroll(session, newPayment)) {
            paymentRepository.save(newPayment);
            return true;
        }

        return false;
    }
}
