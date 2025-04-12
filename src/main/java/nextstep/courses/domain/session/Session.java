package nextstep.courses.domain.session;

import java.time.LocalDate;
import nextstep.payments.domain.Payment;

public class Session {

    private Long id;

    private String title;

    private SessionType sessionType;

    private SessionDate sessionDate;

    private SessionStatus status;

    private int enrollmentCount;

    public Session(Long id, String title, SessionType sessionType, LocalDate startDate,
            LocalDate finishDate, SessionStatus status, int enrollmentCount) {
        this.id = id;
        this.title = title;
        this.sessionType = sessionType;
        this.sessionDate = new SessionDate(startDate, finishDate);
        this.status = status;
        this.enrollmentCount = enrollmentCount;
    }

    public boolean enrollmentCountOver(int enrollmentCount) {
        return this.enrollmentCount >= enrollmentCount;
    }

    public boolean enroll(Payment payment) {
        if (status.canEnroll() && sessionType.enroll(payment, this)) {
            enrollUser(payment.getNsUserId());
            return true;
        }

        return false;
    }

    private void enrollUser(Long nsUserId) {
        // 수강 신청 여부를 DB에 등록? 수강신청한 유저 명단을 어떻게 관리해야 할지 요구 사항 명확하지 않음
        enrollmentCount++;
    }
}
