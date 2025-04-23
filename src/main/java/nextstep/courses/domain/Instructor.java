package nextstep.courses.domain;

public class Instructor {
    private final Long id;
    private final String name;
    private final String email;
    private final int age;
    private final Sessions sessions;

    public Instructor(Long id, String name, String email, int age, Sessions sessions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.sessions = sessions;
    }

    public void approveSession(Session session, Student student) {
        validateForDecision(session, student);
        // approve logic
    }

    public void declineSession(Session session, Student student) {
        validateForDecision(session, student);
        // decline logic
    }

    private void validateForDecision(Session session, Student student) {
        validateSession(session);
        validateStudent(session, student);
    }

    private void validateSession(Session session) {
        if (!sessions.contains(session)) {
            throw new IllegalArgumentException("해당 강의의 강사가 아닙니다 : " + session.getId());
        }
    }

    private void validateStudent(Session session, Student student) {
        if (session.getType() == SessionType.FREE && !student.getPremiumPlan().isWooteco()) {
            throw new IllegalArgumentException("무료 강의는 우아한테크코스 수강생만 가능합니다.");
        }
        if (session.getType() == SessionType.PAID && !student.getPremiumPlan().isWootecopro()) {
            throw new IllegalArgumentException("유료 강의는 프리미엄 플랜이 필요합니다.");
        }
    }
}
