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

    public EnrollStatus approveSession(Session session) {
        validateSession(session);
        return EnrollStatus.PASS;
    }

    public EnrollStatus denySession(Session session) {
        validateSession(session);
        return EnrollStatus.FAIL;
    }

    private void validateSession(Session session) {
        if (!sessions.contains(session)) {
            throw new IllegalArgumentException("해당 강의의 강사가 아닙니다 : " + session.getId());
        }
    }
}
