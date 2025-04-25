package nextstep.session.exception;

public class PaidSessionNotEnrollingException extends PaidSessionException {
    public PaidSessionNotEnrollingException() {
        super("유료 강의가 등록 가능한 상태가 아닙니다.");
    }
}
