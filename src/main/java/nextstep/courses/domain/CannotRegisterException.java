package nextstep.courses.domain;

public class CannotRegisterException extends RuntimeException {
    public CannotRegisterException(String message) {
        super(message);
    }
}
