package nextstep.students.domain;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.Sessions;

import java.util.ArrayList;

public class Student {
    private final Long id;
    private final String name;
    private final String email;
    private final Long budget;
    private Sessions sessions;

    public Student(String name, String email, Long budget) {
        this(0L, name, email, budget);
    }

    public Student(Long id, String name, String email, Long budget) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.sessions = new Sessions(new ArrayList<>());
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

    public boolean isAlreadyRegistered(Long sessionId) {
        return sessions.isAlreadyIncluded(sessionId);
    }

    public void registerSession(Session session) {
        this.sessions = sessions.addSession(session);
    }
}
