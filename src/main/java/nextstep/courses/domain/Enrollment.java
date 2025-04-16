package nextstep.courses.domain;

/**
 * 누가 어떤 강의에 등록했는지에 대한 정보를 담고 있는 클래스
 */
public class Enrollment {
    private Long nsUserId;
    private Long sessionId;

    public Enrollment(Long nsUserId, Long sessionId) {
        this.nsUserId = nsUserId;
        this.sessionId = sessionId;
    }

    public Long nsUserId() {
        return nsUserId;
    }
}
