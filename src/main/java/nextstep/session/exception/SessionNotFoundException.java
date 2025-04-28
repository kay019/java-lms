package nextstep.session.exception;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException(Long sessionId) {
        super(String.format("세션을 찾을 수 없습니다. ID: %d", sessionId));
    }
}
