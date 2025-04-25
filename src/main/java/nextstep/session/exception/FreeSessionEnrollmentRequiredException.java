package nextstep.session.exception;

public class FreeSessionEnrollmentRequiredException extends FreeSessionException {
    public FreeSessionEnrollmentRequiredException() {
        super("무료 세션은 수강 신청이 필요합니다.");
    }
}
