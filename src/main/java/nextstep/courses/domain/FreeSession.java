package nextstep.courses.domain;

import java.time.LocalDateTime;

public class FreeSession extends CourseSession {
    
    public FreeSession() {
        super();
    }

    public FreeSession(SessionStatus status) {
        super(status);
    }

    @Override
    public SessionType getType() {
        return SessionType.FREE;
    }
} 
