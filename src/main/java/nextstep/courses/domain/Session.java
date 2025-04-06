package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUsers;

public class Session {
    private Long id;
    private Course course;
    private final NsUsers nsUsers = new NsUsers();
    private CoverImage coverImage;
    private SessionStatus sessionStatus;
    private RegistrationPolicy registrationPolicy;
    private SessionPeriod sessionPeriod;

    Session(long id, CoverImage coverImage, SessionStatus sessionStatus, RegistrationPolicy registrationPolicy, SessionPeriod sessionPeriod) {
        this.id = id;
        this.coverImage = coverImage;
        this.sessionStatus = sessionStatus;
        this.registrationPolicy = registrationPolicy;
        this.sessionPeriod = sessionPeriod;
    }

    public void toCourse(Course course) {
        this.course = course;
    }

    public int getStudentCount() {
        return nsUsers.getSize();
    }

    public Payment register(NsUser nsUser, Money paymentAmount) {
        if (!sessionStatus.isRegistrable()) {
            throw new IllegalStateException("수강신청이 불가능한 상태입니다.");
        }

        registrationPolicy.validateRegistration(this, paymentAmount);

        nsUsers.add(nsUser);

        return new Payment("", this, nsUser, paymentAmount);
    }


}
