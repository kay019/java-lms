package nextstep.courses;

public class CannotRegisterException extends RuntimeException {
    public CannotRegisterException(String message) {
        super(message);
    }
}
