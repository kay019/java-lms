package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionFactory {

    public static Session ofPaid(CoverImage coverImage, SessionStatus sessionStatus, int sessionFee, int maxStudentCount, LocalDateTime startedAt, LocalDateTime endedAt) {
        RegistrationPolicy registrationPolicy = new PaidRegistrationPolicy(sessionFee, maxStudentCount);
        return new Session(coverImage, sessionStatus, registrationPolicy, startedAt, endedAt);
    }

    public static Session ofFree(CoverImage coverImage, SessionStatus sessionStatus, LocalDateTime startedAt, LocalDateTime endedAt) {
        return new Session(coverImage, sessionStatus, new FreeRegistrationPolicy(), startedAt, endedAt);
    }

}
