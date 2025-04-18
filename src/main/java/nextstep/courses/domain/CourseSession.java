package nextstep.courses.domain;

import nextstep.common.domain.Audit;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class CourseSession {

    private SessionIdentity identity;
    private String title;
    private String description;
    private SessionPeriod period;
    private Image image;
    private SessionStatus status = SessionStatus.PREPARING;
    private SessionType type;
    private final List<Registration> registrations = new ArrayList<>();
    private Audit audit;

    protected CourseSession() {}

    protected CourseSession(SessionStatus status) {
        this.status = status;
    }

    protected void validateRegistration(){
        if (!SessionStatus.RECRUITING.equals(status)) {
            throw new IllegalArgumentException("강의 상태가 모집중일 때만 수강 신청이 가능합니다");
        }
    }

    public abstract SessionType getType();

    public Long getId() {
        return identity != null ? identity.getId() : null;
    }

    public Registration addRegistration(final CourseSession session, final NsUser user, final List<Payment> payments) {
        validateRegistration();

        Registration registration = new Registration(session, user);
        registrations.add(registration);
        return registration;
    }
    
    public static class SessionIdentity {
        private final Long id;
        private final Long courseId;
        
        public SessionIdentity(final Long id, final Long courseId) {
            this.id = id;
            this.courseId = courseId;
        }
        
        public Long getId() {
            return id;
        }
    }
    
    public static class SessionPeriod {
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
        
        public SessionPeriod(final LocalDateTime startDate, final LocalDateTime endDate) {
            if (endDate.isBefore(startDate)) {
                throw new IllegalArgumentException("종료일은 시작일보다 이전일 수 없습니다.");
            }
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}
