package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final Long id;
    private final String name;
    private final String email;
    private final Long budget;
    private final EnrollStatus enrollStatus;
    private Sessions sessions;

    public Student(Long id, String name, String email, Long budget, EnrollStatus status, Sessions sessions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.sessions = sessions;
        this.enrollStatus = status;
    }

    public Student(String name, String email, Long budget, EnrollStatus status) {
        this(0L, name, email, budget, status);
    }

    public Student(Long id, String name, String email, Long budget, EnrollStatus status) {
        this(id, name, email, budget, status, new Sessions(new ArrayList<>()));
    }

    public Student withSessions(Sessions sessions) {
        return new Student(id, name, email, budget, enrollStatus, sessions);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getBudget() {
        return budget;
    }

    public boolean isBudgetOver(Long fee) {
        return fee > budget;
    }

    public boolean isAlreadyRegistered(Session session) {
        return sessions.contains(session);
    }

    public void registerSession(Session session) {
        this.sessions = sessions.addSession(session);
    }

    public List<Long> getSessionIds() {
        return sessions.getSessionIds();
    }

    public EnrollStatus getEnrollStatus() {
        return enrollStatus;
    }

    public boolean isPrivileged() {
        return enrollStatus == EnrollStatus.PASS;
    }
}
