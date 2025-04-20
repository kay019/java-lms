package nextstep.courses.domain;

import lombok.Getter;
import nextstep.payments.domain.Payment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Session {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Embedded
    private CoverImage coverImage;

    @Column(nullable = false)
    private boolean free;

    @Column(nullable = false)
    private long amount;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    @Getter
    @Column(name = "enrolled_count", nullable = false)
    private int enrolledCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status;

    protected Session() {
    }

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

    public Session(Long id, CoverImage coverImage, boolean free, long amount, int maxCapacity, SessionStatus status) {
        this(id, LocalDateTime.now(), LocalDateTime.now(), coverImage, free, amount, maxCapacity, status);
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
