package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private final List<Long> enrolledUserIds = new ArrayList<>();// 준비중, 모집중, 종료
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CoverImage coverImaage;
    private boolean free;
    private long amount;
    private int maxCapacity;
    private int enrolledCount;
    private SessionStatus status;

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, CoverImage coverImaage, boolean free, long amount, int maxCapacity, SessionStatus status) {
    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, CoverImage coverImage, boolean free, long amount, int maxCapacity, SessionStatus status) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImage = coverImage;
        this.free = free;
        this.amount = amount;
        this.maxCapacity = maxCapacity;
        this.status = status;
        this.enrolledCount = 0;
    }

    public Session(Long id, CoverImage coverImaage, boolean free, long amount, int maxCapacity, SessionStatus status) {
        this(id, LocalDateTime.now(), LocalDateTime.now(), coverImaage, free, amount, maxCapacity, status);
    }

    public void enroll(Payment payment) {
        if (!this.status.equals(SessionStatus.OPEM)) {
            throw new IllegalArgumentException("강의 수강신청은 강의 상태가 모집중일 때만 가능합니다.");
        }

        if (free) {
            enrollFreeUser();
        } else {
            enrollPaidUser(payment);
        }
    }

    public void enrollFreeUser() {
        enrolledCount++;
    }

    public void enrollPaidUser(Payment payment) {
        validPaidSessionEnroll(payment);
        enrolledCount++;
    }

    private void validPaidSessionEnroll(Payment payment) {
        if (getEnrolledCount() >= maxCapacity) {
            throw new IllegalArgumentException("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
        }
        if (this.amount != payment.getAmount()) {
            throw new IllegalArgumentException("유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능합니다.");
        }
    }
}
