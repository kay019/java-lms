package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.students.domain.Student;
import nextstep.students.domain.Students;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Session {
    protected final Long id;
    protected final Period period;
    protected final CoverImage coverImage;
    protected final SessionStatus status;
    protected final SessionType type;
    protected Students students;

    protected Session(Long id, LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionLifeCycle status, SessionRecruitStatus recruitStatus, SessionType type) {
        this(id, startDate, endDate, coverImage, status, recruitStatus, type, new Students(new ArrayList<>()));
    }

    protected Session(Long id, LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionLifeCycle status, SessionRecruitStatus recruitStatus, SessionType type, Students students) {
        this.id = id;
        this.period = new Period(startDate, endDate);
        this.coverImage = coverImage;
        this.status = new SessionStatus(status, recruitStatus);
        this.type = type;
        this.students = students;
    }

    public Payment registerStudent(Student student) {
        validateEnrollment(student);
        this.students = this.students.addStudent(student);
        student.registerSession(id);
        return createPayment(student);
    }

    public void validateEnrollment(Student student) {
        validateSessionStatus();
        validateAlreadyRegistered(student);
        validateBudgetOver(student);
        validateLimitedCapacity();
    }

    protected void validateBudgetOver(Student student) {
    }

    protected void validateLimitedCapacity() {
    }

    protected Payment createPayment(Student student) {
        return null;
    }

    private void validateAlreadyRegistered(Student student) {
        if (student.isAlreadyRegistered(id)) {
            throw new IllegalStateException("이미 등록한 강의입니다.");
        }
    }

    private void validateSessionStatus() {
        if (!status.isInRecruit()) {
            throw new IllegalArgumentException("세션이 '모집 중' 일때만 수강 신청이 가능합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Period getPeriod() {
        return period;
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

    public Students getStudents() {
        return students;
    }
}
