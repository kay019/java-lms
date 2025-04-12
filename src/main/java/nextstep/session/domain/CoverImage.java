package nextstep.session.domain;

import nextstep.Identifiable;

public class CoverImage implements Identifiable {

    private Long id;
    private Long sessionId;
    private final CoverImageFileSize size;
    private final CoverImageType type;
    private final CoverImageResolution resolution;

    public CoverImage(Long id, Long sessionId, CoverImageType type, long size, int width, int height) {
        this.id = id;
        this.sessionId = sessionId;
        this.size = new CoverImageFileSize(size);
        this.type = type;
        this.resolution = new CoverImageResolution(width, height);
    }

    public CoverImage(CoverImageType type, long size, int width, int height) {
        this(null, null, type, size, width, height);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public long getSize() {
        return size.getSize();
    }

    public CoverImageType getType() {
        return type;
    }

    public int getWidth() {
        return resolution.getWidth();
    }

    public int getHeight() {
        return resolution.getHeight();
    }

    @Override
    public boolean isUnsaved() {
        return getId() == null;
    }
}
