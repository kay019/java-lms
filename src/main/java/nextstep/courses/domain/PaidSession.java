package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.ArrayList;

public class PaidSession extends Session {
    private final Integer capacity;
    private final Long fee;

    public PaidSession(Long id, Period period, CoverImages coverImages, SessionStatus status, ParticipantType participantType, Integer capacity, Long fee, Students students) {
        super(id, period, coverImages, status, new SessionType(PricingType.PAID, participantType), students);
        this.capacity = capacity;
        this.fee = fee;
    }

    public PaidSession(Long id, Period period, CoverImages coverImages, SessionStatus status, ParticipantType participantType, Integer capacity, Long fee) {
        this(id, period, coverImages, status, participantType, capacity, fee, new Students(new ArrayList<>()));
    }

    public PaidSession(Period period, CoverImages coverImages, SessionStatus status, ParticipantType participantType, Integer capacity, Long fee) {
        this(0L, period, coverImages, status, participantType, capacity, fee, new Students(new ArrayList<>()));
    }

    @Override
    public PaidSession withStudents(Students students) {
        return new PaidSession(id, period, coverImages, status, type.getParticipantType(), capacity, fee, students);
    }

    @Override
    public PaidSession withCoverImages(CoverImages coverImages) {
        return new PaidSession(id, period, coverImages, status, type.getParticipantType(), capacity, fee, students);
    }

    @Override
    protected void validateBudgetOver(Student student) {
        if (student.isBudgetOver(fee)) {
            throw new IllegalArgumentException("예산이 부족하여 강의를 신청할 수 없습니다.");
        }
    }

    @Override
    protected void validateLimitedCapacity() {
        System.out.println("students = " + students);
        if (students.getSize() == capacity) {
            throw new IllegalArgumentException("정원이 초과되었습니다.");
        }
    }

    @Override
    protected Payment createPayment(Student student) {
        return new Payment(id, student.getId(), fee);
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Long getFee() {
        return fee;
    }
}
