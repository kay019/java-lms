package nextstep.session.image.entity;

public class SessionCoverImageEntity {
    private final long id;
    private final long sessionId;
    private final int size;
    private final String type;
    private final int width;
    private final int height;

    public SessionCoverImageEntity(long id, long sessionId, int size, String type, int width, int height) {
        this.id = id;
        this.sessionId = sessionId;
        this.size = size;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public long getId() {
        return id;
    }

    public long getSessionId() {
        return sessionId;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
