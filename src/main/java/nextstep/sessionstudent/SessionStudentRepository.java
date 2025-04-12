package nextstep.sessionstudent;

public interface SessionStudentRepository {
    SessionStudent findById(long id);
    SessionStudents findBySessionId(long sessionId);
    int save(SessionStudent sessionStudent);
}
