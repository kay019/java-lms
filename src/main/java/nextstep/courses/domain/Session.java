package nextstep.courses.domain;

import nextstep.courses.domain.enroll.SessionEnroll;
import nextstep.courses.domain.image.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {
    private Long id;

    private CoverImage coverImage;

    private SessionEnroll enroll;

    private SessionStatus status;

    private Students students;

    private SessionDate date;

    public Session(Long id, CoverImage coverImage,
                   SessionEnroll enroll, SessionStatus status,
                   SessionDate date) {
        this.id = id;
        this.coverImage = coverImage;
        this.enroll = enroll;
        this.status = status;
        this.students = new Students();
        this.date = date;
    }

    public void enroll(Payment payment) {
        status.validateEnroll();
        enroll.validateEnroll(payment, this);
        enrollStudent(payment.getNsUser());
    }

    private void enrollStudent(NsUser user) {
        students.add(user);
    }

    public void validateSessionFullStudent(int maxStudent) {
        if (students.size() >= maxStudent) {
            throw new IllegalArgumentException("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
        }
    }

    public int getStudentSize() {
        return students.size();
    }
}
