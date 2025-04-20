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

    public static Payment payment(long amount) {
        return new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), amount);
    }

    public static PaidSession paidSession(int max, long fee) {
        return new PaidSession(DEFAULT_DATE, DEFAULT_IMAGE, SessionStatus.OPEN, EnrollmentTest.ENROLLMENTS, max, fee);
    }

    public static Session freeSession() {
        return new Session(DEFAULT_DATE, DEFAULT_IMAGE, SessionStatus.OPEN, EnrollmentTest.ENROLLMENTS);
    }
}
