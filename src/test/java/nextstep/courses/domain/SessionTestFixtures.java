package nextstep.courses.domain;

import nextstep.courses.session.domain.*;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;

import java.time.LocalDateTime;

public class SessionTestFixtures {
    private static final SessionDate DEFAULT_DATE =
            new SessionDate(LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 0, 0));

    private static final SessionCoverImage DEFAULT_IMAGE =
            new SessionCoverImage(1, SessionImageType.from("gif"), 300, 200);

    public static Session paidSession(int max, long fee) {
        return Session.paid(1L, 1L, DEFAULT_DATE, DEFAULT_IMAGE, SessionStatus.OPEN, EnrollmentTest.ENROLLMENTS, max, fee);
    }

    public static Session freeSession() {
        return Session.free(1L, 1L, DEFAULT_DATE, DEFAULT_IMAGE, SessionStatus.OPEN, EnrollmentTest.ENROLLMENTS);
    }
}
