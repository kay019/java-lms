package nextstep.sessions.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.Objects;

public abstract class Session {
    private final Long id;
    private final Long courseId;
    private final Period sessionPeriod;
    private final ImageInfo coverImage;
    private final SessionStatus sessionStatus;
    private final Registration registration;

    public Session(Long id, Long courseId, Period sessionPeriod, ImageInfo imageInfo, SessionStatus sessionStatus, Registration registration) {
        this.id = id;
        this.courseId = courseId;
        this.sessionPeriod = sessionPeriod;
        this.coverImage = imageInfo;
        this.sessionStatus = sessionStatus;
        this.registration = registration;
    }

    public Payment register(NsUser user, Long amount) {
        if (!sessionStatus.isOpened()) {
            throw new IllegalStateException();
        }
        if (!canRegister(user, amount)) {
            throw new IllegalStateException();
        }
        registration.register(user);
        return new Payment(makePaymentId(user), this.id, user.getId(), amount);
    }

    protected abstract boolean canRegister(NsUser user, Long amount);

    protected int registeredUserCount() {
        return registration.registeredUserCount();
    }

    private String makePaymentId(NsUser user) {
        return this.id + "|" + user.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionPeriod, session.sessionPeriod) && Objects.equals(coverImage, session.coverImage) && sessionStatus == session.sessionStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionPeriod, coverImage, sessionStatus);
    }
}
