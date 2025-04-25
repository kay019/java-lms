package nextstep.session.exception;

public class FreeSessionInvalidEnrollmentException extends FreeSessionException {
    public FreeSessionInvalidEnrollmentException() {
        super("무료 강의는 수강료를 지불하지 않고 수강신청할 수 있습니다.");
    }
}
