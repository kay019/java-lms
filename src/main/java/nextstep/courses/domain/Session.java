package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 강의
 */
public class Session {
    // 강의 아이디
    private Long id;
    // 과정
    private Course course;
    // 시작일과 종료일
    private Term term;
    // 커버 이미지
    private Cover cover;
    // 강의 타입 유/무료
    private ChargeType chargeType;
    // 최대 수강 인원
    private int maxParticipants;
    // 현재 수강 인원
    private List<Enrollment> participants;
    // 강의 상태
    private SessionStatus status;
    // 강의료
    private Long amount;

    private Session(Course course, Term term, Cover cover, ChargeType chargeType, int maxParticipants, long amount) {
        this.course = course;
        this.term = term;
        this.cover = cover;
        this.chargeType = chargeType;
        this.maxParticipants = maxParticipants;
        this.participants = new ArrayList<>();
        this.status = SessionStatus.PREPARING;
        this.amount = amount;
    }

    public static Session createFreeSession(Course course, Term term, Cover cover) {
        return new Session(course, term, cover, ChargeType.FREE, Integer.MAX_VALUE, 0L);
    }

    public static Session createPaidSession(Course course, Term term, Cover cover, int maxParticipants, long amount) {
        return new Session(course, term, cover, ChargeType.PAID, maxParticipants, amount);
    }

    public void enroll(Payment payment) {
        if (!isOpen()) {
            throw new IllegalStateException("모집 중인 강의만 수강 신청할 수 있습니다.");
        }
        if (isPaid() && isFull()) {
            throw new IllegalStateException("정원이 가득 찼습니다.");
        }
        if (isPaid() && !amount.equals(payment.amount())) {
            throw new IllegalStateException("결제 금액이 수강료와 일치하지 않습니다.");
        }
        participants.add(new Enrollment(payment.userId(), this.id));
    }

    private boolean isPaid() {
        return ChargeType.PAID == chargeType;
    }

    private boolean isOpen() {
        return status == SessionStatus.OPEN;
    }

    private boolean isFull() {
        return participants.size() >= maxParticipants;
    }

    public void open() {
        if (status != SessionStatus.PREPARING) {
            throw new IllegalStateException("강의 상태가 모집중이 아닙니다.");
        }
        status = SessionStatus.OPEN;
    }

    public Long id() {
        return id;
    }

    public boolean isEnrolled(Long userId) {
        if (Objects.isNull(userId)) {
            return false;
        }
        return participants.stream()
                .anyMatch(it -> userId.equals(it.nsUserId()));
    }

}
