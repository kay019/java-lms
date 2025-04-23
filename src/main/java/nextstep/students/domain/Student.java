package nextstep.students.domain;

import nextstep.courses.domain.Sessions;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final Long id;
    private final String name;
    private final String email;
    private final Long budget;
    private Sessions sessionIds;

    public Student(Long id, String name, String email, Long budget, Sessions sessionIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.sessionIds = sessionIds;
    }

    public Student(String name, String email, Long budget) {
        this(0L, name, email, budget);
    }

    public Student(Long id, String name, String email, Long budget) {
        this(id, name, email, budget, new Sessions(new ArrayList<>()));
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
        return sessionIds.isAlreadyIncluded(sessionId);
    }

    public void registerSession(Long sessionId) {
        this.sessionIds = sessionIds.addSession(sessionId);
    }

    public List<Long> getSessionIds() {
        return sessionIds.getSessionIds();
    }
}
