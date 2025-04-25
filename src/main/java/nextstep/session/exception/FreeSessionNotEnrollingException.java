package nextstep.session.exception;

public class FreeSessionNotEnrollingException extends FreeSessionException {
    public FreeSessionNotEnrollingException() {
        super("무료 세션을 수강신청할 수 없습니다.");
    }
}
