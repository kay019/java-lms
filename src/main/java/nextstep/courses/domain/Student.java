package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final Long id;
    private final String name;
    private final String email;
    private final Long budget;
    private Sessions sessions;

    public Student(Long id, String name, String email, Long budget, Sessions sessions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.sessions = sessions;
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

    public boolean isAlreadyRegistered(Session session) {
        return sessions.isAlreadyIncluded(session);
    }

    public void registerSession(Session session) {
        this.sessions = sessions.addSession(session);
    }

    public List<Long> getSessionIds() {
        return sessions.getSessionIds();
    }
}
