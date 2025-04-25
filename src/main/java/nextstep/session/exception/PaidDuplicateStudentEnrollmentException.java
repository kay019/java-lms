package nextstep.session.exception;

public class PaidDuplicateStudentEnrollmentException extends PaidSessionException {
    public PaidDuplicateStudentEnrollmentException() {
        super("이미 유료 강의를 수강신청한 학생입니다.");
    }
}
