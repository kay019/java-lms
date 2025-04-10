package nextstep.courses.domain;

public class CannotCreateSessionException extends RuntimeException {
    public CannotCreateSessionException(String message) {
        super(message);
    }
}
