package nextstep.courses.domain;

public class CannotUploadImageException extends RuntimeException {
    public CannotUploadImageException(String message) {
        super(message);
    }
}
