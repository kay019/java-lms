package nextstep.enrollment.entity;

public class StudentEntity {
    public static final String COL_ID = "id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_SESSION_ID = "session_id";
    public static final String COL_NAME = "name";

    private final long id;
    private final long userId;
    private final long sessionId;
    private final String name;

    public StudentEntity(long id, long userId, long sessionId, String name) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }
}
