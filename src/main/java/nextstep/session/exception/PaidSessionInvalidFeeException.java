package nextstep.session.exception;

public class PaidSessionInvalidFeeException extends PaidSessionException {
    public PaidSessionInvalidFeeException() {
        super("유료 강의의 수강료가 일치하지 않습니다.");
    }
}
