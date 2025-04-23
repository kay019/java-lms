package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class Student {
    private String userId;

    private String name;

    private String email;

    public Student(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Student(NsUser user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
