package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.students.domain.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Session {
    private final Long id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final CoverImage coverImage;
    private final SessionStatus status;
    private final SessionType type;
    private final Integer capacity;
    private final Long fee;
    private Students students;

    public Session(LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionStatus status) {
        this(0L, startDate, endDate, coverImage, status, SessionType.FREE, null, null);
    }

    public Session(Long id, LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionStatus status, SessionType type, Integer capacity, Long fee) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImage = coverImage;
        this.status = status;
        this.type = type;
        this.capacity = capacity;
        this.fee = fee;
        this.students = new Students(new ArrayList<>());
    }

    public Payment registerStudent(Student student) {
        validateEnrollment(student);
        this.students = this.students.addStudent(student);
        student.registerSession(this);
        return new Payment(id, student.getId(), fee);
    }

    public void validateEnrollment(Student student) {
        if (status != SessionStatus.OPEN) {
            throw new IllegalArgumentException("세션이 '모집 중' 일때만 수강 신청이 가능합니다.");
        }
        if (type == SessionType.PAID) {
            if (students.getSize() == capacity) {
                throw new IllegalArgumentException("정원이 초과되었습니다.");
            }
            if (student.isBudgetOver(fee)) {
                throw new IllegalArgumentException("예산이 부족하여 강의를 신청할 수 없습니다.");
            }
        }
        if (student.isAlreadyRegistered(id)) {
            throw new IllegalStateException("이미 등록한 강의입니다.");
        }
    }

    public boolean sessionIdMatches(Long id) {
        return Objects.equals(this.id, id);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public SessionType getType() {
        return type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Long getFee() {
        return fee;
    }
}
