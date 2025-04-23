package nextstep.session.mapper;

import java.util.List;
import java.util.stream.Collectors;

import nextstep.enrollment.domain.Student;
import nextstep.enrollment.mapper.StudentMapper;
import nextstep.session.domain.FreeSession;
import nextstep.session.domain.PaidSession;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionDate;
import nextstep.session.domain.SessionStatus;
import nextstep.session.entity.SessionEntity;

public class SessionMapper {
    private static final int FREE = 0;
    private static final int NO_LIMIT = 0;

    public SessionEntity toEntity(Session session) {
        if (session instanceof PaidSession) {
           return toPaidEntity((PaidSession) session);
        }

        return toFreeEntity((FreeSession) session);
    }

    private SessionEntity toPaidEntity(PaidSession session) {
        return new SessionEntity(
                session.getId(),
                session.getCourseId(),
                session.getStatus().getProgressStatusName(),
                session.getStatus().getEnrollmentStatusName(),
                session.getFee(),
                session.getMaxCapacity(),
                session.getSessionDate().getStartDate(),
                session.getSessionDate().getEndDate(),
                session.getStudents()
        );
    }

    private SessionEntity toFreeEntity(FreeSession session) {
        return new SessionEntity(
                session.getId(),
                session.getCourseId(),
                session.getStatus().getProgressStatusName(),
                session.getStatus().getEnrollmentStatusName(),
                FREE,
                NO_LIMIT,
                session.getSessionDate().getStartDate(),
                session.getSessionDate().getEndDate(),
                session.getStudents()
        );
    }

    public Session toDomain(SessionEntity entity) {
        SessionDate sessionDate = new SessionDate(
            entity.getStartDate(),
            entity.getEndDate()
        );

        StudentMapper studentMapper = new StudentMapper();
        List<Student> students = entity.getStudentEntities().stream()
            .map(studentMapper::toDomain)
            .collect(Collectors.toList());

        if (isPaidSession(entity)) {
            return getPaidSession(entity, sessionDate, students);
        }

        return getFreeSession(entity, sessionDate, students);
    }

    private boolean isPaidSession(SessionEntity entity) {
        return entity.getFee() > FREE;
    }

    private static FreeSession getFreeSession(SessionEntity entity, SessionDate sessionDate, List<Student> students) {
        return new FreeSession.Builder()
            .id(entity.getId())
            .courseId(entity.getCourseId())
            .status(new SessionStatus(entity.getProgressStatus(), entity.getEnrollmentStatus()))
            .sessionDate(sessionDate)
            .students(students)
            .build();
    }

    private static PaidSession getPaidSession(SessionEntity entity, SessionDate sessionDate, List<Student> students) {
        return new PaidSession.Builder()
            .id(entity.getId())
            .courseId(entity.getCourseId())
            .status(new SessionStatus(entity.getProgressStatus(), entity.getEnrollmentStatus()))
            .fee(entity.getFee())
            .maxCapacity(entity.getCapacity())
            .sessionDate(sessionDate)
            .students(students)
            .build();
    }
}
