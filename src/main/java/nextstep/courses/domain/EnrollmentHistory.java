package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class EnrollmentHistory {
    private Long id;

    private NsUser user;

    private Session session;

    private LocalDateTime enrolledAt;


    public EnrollmentHistory(NsUser user, Session session, LocalDateTime enrolledAt) {
        this.user = user;
        this.session = session;
        this.enrolledAt = enrolledAt;
    }
}
