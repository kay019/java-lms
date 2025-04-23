package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.students.domain.Student;
import nextstep.students.domain.Students;

import java.time.LocalDate;

public class FreeSession extends Session {
    public FreeSession(Long id, LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionLifeCycle status, SessionRecruitStatus recruitStatus, Students students) {
        super(id, startDate, endDate, coverImage, status, recruitStatus, SessionType.FREE, students);
    }

    public FreeSession(LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionLifeCycle status, SessionRecruitStatus recruitStatus) {
        super(0L, startDate, endDate, coverImage, status, recruitStatus, SessionType.FREE);
    }

    @Override
    protected Payment createPayment(Student student) {
        return new Payment(id, student.getId(), 0L);
    }
}
