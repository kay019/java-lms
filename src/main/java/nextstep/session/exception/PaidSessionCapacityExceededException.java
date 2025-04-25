package nextstep.session.exception;

public class PaidSessionCapacityExceededException extends PaidSessionException {
    public PaidSessionCapacityExceededException() {
        super("유료 강의의 수강 인원이 초과되었습니다.");
    }
}
