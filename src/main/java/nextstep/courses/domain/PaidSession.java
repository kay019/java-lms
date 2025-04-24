package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class PaidSession extends Session {
    private final int fee;
    private final int maxStudent;

    public PaidSession(Long id, SessionStatus status, SessionDate date,
                       LocalDateTime createdAt, LocalDateTime updatedAt,
                       int fee, int maxStudent) {
        super(id, status, date, createdAt, updatedAt);
        this.fee = fee;
        this.maxStudent = maxStudent;
    }

    public PaidSession(Long id, SessionStatus status, SessionDate date, int fee, int maxStudent) {
        super(id, status, date);
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
        Student student = new Student(user);
        students.add(student);
    }

    private void validateEnroll(Payment payment) {
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

    public int getFee() {
        return fee;
    }

    public int getMaxStudent() {
        return maxStudent;
    }
}
