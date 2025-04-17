package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class Student {
    private final long id;
    private final String name;

    public Student(NsUser user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
