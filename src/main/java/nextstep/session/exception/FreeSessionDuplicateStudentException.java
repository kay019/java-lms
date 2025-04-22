package nextstep.session.exception;

public class FreeSessionDuplicateStudentException extends FreeSessionException{
    public FreeSessionDuplicateStudentException() {
        super("이미 수강신청한 학생입니다.");
    }
}
