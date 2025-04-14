package nextstep.application.service;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionType;
import nextstep.students.domain.Student;
import org.springframework.stereotype.Service;

@Service
public class SessionRegistrationValidator {
    public void validate(Session session, Student student) {
        if (session.getStatus() != SessionStatus.OPEN) {
            throw new IllegalArgumentException("세션이 '모집 중' 일때만 수강 신청이 가능합니다.");
        }
        if (session.getType() == SessionType.PAID) {
            if (session.getStudentsCount() == session.getCapacity()) {
                throw new IllegalArgumentException("정원이 초과되었습니다.");
            }
            if (session.getFee() > student.getBudget()) {
                throw new IllegalArgumentException("예산이 부족하여 강의를 신청할 수 없습니다.");
            }
        }
    }
}
