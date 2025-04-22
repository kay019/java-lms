package nextstep.session.exception;

public class PaidSessionIllegalArgumentException extends PaidSessionException {
    public PaidSessionIllegalArgumentException() {
        super("enrollment 는 null 이 아니어야 합니다.");
    }
}
