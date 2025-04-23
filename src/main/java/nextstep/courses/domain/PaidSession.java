package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.students.domain.Student;
import nextstep.students.domain.Students;

import java.time.LocalDate;
import java.util.ArrayList;

public class PaidSession extends Session {
    private final Integer capacity;
    private final Long fee;

    public PaidSession(Long id, LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionLifeCycle status, SessionRecruitStatus recruitStatus, Integer capacity, Long fee, Students students) {
        super(id, startDate, endDate, coverImage, status, recruitStatus, SessionType.PAID, students);
        this.capacity = capacity;
        this.fee = fee;
    }

    public PaidSession(Long id, LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionLifeCycle status, SessionRecruitStatus recruitStatus, Integer capacity, Long fee) {
        this(id, startDate, endDate, coverImage, status, recruitStatus, capacity, fee, new Students(new ArrayList<>()));
    }

    public PaidSession(LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionLifeCycle status, SessionRecruitStatus recruitStatus, Integer capacity, Long fee) {
        this(startDate, endDate, coverImage, status, recruitStatus, capacity, fee, new Students(new ArrayList<>()));
    }

    public PaidSession(LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionLifeCycle status, SessionRecruitStatus recruitStatus, Integer capacity, Long fee, Students students) {
        this(0L, startDate, endDate, coverImage, status, recruitStatus, capacity, fee, students);
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
