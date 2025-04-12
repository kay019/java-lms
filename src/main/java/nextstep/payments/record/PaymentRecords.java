package nextstep.payments.record;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUsers;

public class PaymentRecords {
    private final List<PaymentRecord> records;

    public PaymentRecords() {
        this(new ArrayList<>());
    }

    public PaymentRecords(List<PaymentRecord> records) {
        this.records = records;
    }

    public List<Long> getNsUserIds() {
        return records.stream()
            .map(PaymentRecord::getNsUserId)
            .distinct()
            .collect(Collectors.toList());
    }

    public void withNsUsers(NsUsers nsUsers) {
        records.forEach(pr -> {
            NsUser user = nsUsers.findById(pr.getNsUserId())
                .orElseThrow(() -> new IllegalArgumentException(
                    "Unknown userId: " + pr.getNsUserId()));
            pr.setNsUser(user);
        });
    }

    public List<PaymentRecord> getRecords() {
        return records;
    }

    public Payments toPayments(Session session) {
        List<Payment> payments = getRecords()
            .stream()
            .map(record -> record.toPayment(session))
            .collect(Collectors.toList());

        return new Payments(payments);
    }
}
