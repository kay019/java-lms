package nextstep.students.domain;

public class Student {
    private final Long id;
    private final String name;
    private final String email;
    private final Long budget;
    private Long sessionId;

    public Student(String name, String email, Long budget) {
        this(0L, name, email, budget);
    }

    public Student(Long id, String name, String email, Long budget) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.budget = budget;
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

    public void addSession(Long sessionId) {
        this.sessionId = sessionId;
    }
}
