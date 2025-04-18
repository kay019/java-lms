package nextstep.courses.domain;

import nextstep.courses.domain.image.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class FreeSession extends Session {
    public FreeSession(Long id, CoverImage coverImage, SessionStatus status, SessionDate date) {
        super(id, coverImage, status, date);
    }

    @Override
    public void enroll(Payment payment) {
        status.validateEnroll();
        enrollStudent(payment.getNsUser());
    }

    private void enrollStudent(NsUser user) {
        students.add(user);
    }

}
