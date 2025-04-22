package nextstep.courses.domain;

public enum SessionType {
    FREE,
    PAID;

    public static SessionType from(String sessionType) {
        return SessionType.valueOf(sessionType);
    }
    
    public static SessionType from(Class<? extends Session> sessionClass) {
        if (sessionClass == FreeSession.class) {
            return FREE;
        } else if (sessionClass == PaidSession.class) {
            return PAID;
        }
        throw new IllegalArgumentException("Invalid session type");
    }
} 