package nextstep.payments.factory;

import nextstep.courses.domain.session.Session;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentEntityUserMap;
import nextstep.payments.domain.Payments;
import nextstep.payments.entity.PaymentEntity;
import nextstep.users.domain.NsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentsFactory {

    private final PaymentFactory paymentFactory;

    @Autowired
    public PaymentsFactory(PaymentFactory paymentFactory) {
        this.paymentFactory = paymentFactory;
    }

    public Payments create(Session session, PaymentEntityUserMap paymentEntityNsUserMap) {
        List<Payment> paymentList = paymentEntityNsUserMap.entrySet().stream()
            .map(entry -> {
                PaymentEntity paymentEntity = entry.getKey();
                NsUser user = entry.getValue();
                return paymentFactory.create(paymentEntity, session, user);
            }).collect(Collectors.toList());

        return new Payments(paymentList);
    }
}
