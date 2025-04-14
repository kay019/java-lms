package nextstep.courses;

public class CannotUploadImageException extends RuntimeException {
    public CannotUploadImageException(String message) {
        super(message);
    }
}
