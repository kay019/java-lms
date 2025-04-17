package nextstep.courses.domain;

import nextstep.courses.domain.image.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PaidSession extends Session {
    private final int fee;
    private final int maxStudent;

    public PaidSession(Long id, CoverImage coverImage, SessionStatus status, SessionDate date, int fee, int maxStudent) {
        super(id, coverImage, status, date);
        this.fee = fee;
        this.maxStudent = maxStudent;
    }

    @Override
    public void enroll(Payment payment) {
        status.validateEnroll();
        validateEnroll(payment);
        enrollStudent(payment.getNsUser());
    }

    private void enrollStudent(NsUser user) {
        students.add(user);
    }

    public void validateEnroll(Payment payment) {
        validateSessionFullStudent();

        if (fee != payment.getAmount()) {
            throw new IllegalArgumentException("수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능합니다.");
        }
    }

    private void validateSessionFullStudent() {
        int currentStudent = getStudentSize();
        if (currentStudent >= maxStudent) {
            throw new IllegalArgumentException("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
        }
    }
}
