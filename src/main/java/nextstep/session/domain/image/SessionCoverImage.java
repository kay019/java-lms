package nextstep.session.domain.image;

public class SessionCoverImage {
    private static final double WIDTH_RATIO = 3;
    private static final double HEIGHT_RATIO = 2;
    private static final int MAX_BYTE_SIZE = 1024 * 1024;

    Rows rows;

    private final SessionCoverImageType sessionCoverImageType;

    public SessionCoverImage(Rows rows, SessionCoverImageType sessionCoverImageType) {
        if ((WIDTH_RATIO * rows.size()) != (HEIGHT_RATIO * rows.rowSize())) {
            throw new IllegalArgumentException("width와 height의 비율은 3:2 이여야 합니다.");
        }
        if (rows.byteSize() > MAX_BYTE_SIZE) {
            throw new IllegalArgumentException("크기가 1MB를 초과했습니다.");
        }

        this.rows = rows;
        this.sessionCoverImageType = sessionCoverImageType;
    }
}
