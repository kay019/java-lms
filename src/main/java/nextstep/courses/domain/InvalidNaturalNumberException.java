package nextstep.courses.domain;

public class InvalidNaturalNumberException extends RuntimeException {
    public InvalidNaturalNumberException(String message) {
        super(message);
    }
}
