package nextstep.courses;

public class CannotCreateSessionException extends RuntimeException {
    public CannotCreateSessionException(String message) {
        super(message);
    }
}
