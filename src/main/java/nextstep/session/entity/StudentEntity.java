package nextstep.session.entity;

public class StudentEntity {
    public static final String COL_ID = "id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_SESSION_ID = "session_id";
    public static final String COL_NAME = "name";
    public static final String COL_STATUS = "status";

    private final long id;
    private final long userId;
    private final long sessionId;
    private final String name;
    private final String status;

    public StudentEntity(long id, long userId, long sessionId, String name, String status) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.name = name;
        this.status = status;
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

    public String getStatus() {
        return status;
    }
}
