package nextstep.payments.domain;

import nextstep.payments.entity.PaymentEntity;
import nextstep.users.domain.NsUser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PaymentEntityUserMap {
    private final Map<PaymentEntity, NsUser> value;

    public PaymentEntityUserMap() {
        this(new HashMap<>());
    }

    public PaymentEntityUserMap(Map<PaymentEntity, NsUser> value) {
        this.value = value;
    }

    public Set<Map.Entry<PaymentEntity, NsUser>> entrySet() {
        return Collections.unmodifiableSet(value.entrySet());
    }

    public void add(PaymentEntity paymentEntity, NsUser nsUser) {
        value.put(paymentEntity, nsUser);
    }
}
