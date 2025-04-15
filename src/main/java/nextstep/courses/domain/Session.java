package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
public abstract class Session {
    protected Long id;
    protected String name;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected Image coverImage;
    protected LectureStatus status;
    protected Set<Long> registeredStudents = new HashSet<>();
    public Session(Long id, String name, LocalDate startDate, LocalDate endDate, Image coverImage, LectureStatus status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImage = coverImage;
        this.status = status;
    }

    public void register(Long studentId, Payment payment) {
        if (status != LectureStatus.RECRUITING) {
            throw new IllegalStateException("수강 신청은 모집중일 때만 가능합니다.");
        }

        validateRegistration(studentId, payment);
        registeredStudents.add(studentId);
    }

    public boolean isRegistered(Long studentId) {
        return registeredStudents.contains(studentId);
    }

    protected abstract void validateRegistration(Long studentId, Payment payment);

    public abstract LectureType getType();
}