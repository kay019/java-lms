package nextstep.sessions.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.util.Objects;

public class Session {
    private final ImageInfo STANDARD_IMAGE_INFO = new ImageInfo(10^6, "jpg", 300, 200);
    private final float IMAGE_RATIO = 1.5f;

    private final Long id;
    private final Period sessionPeriod;
    private final ImageInfo coverImage;
    private final SessionStatus sessionStatus;
    private final Registration registration;

    public Session(Long id, LocalDate startDate, LocalDate endDate, ImageInfo imageInfo) {
        this(id, new Period(startDate, endDate), imageInfo, SessionStatus.READY, new Registration(RegistrationType.FREE));
    }

    public Session(Long id, Period sessionPeriod, ImageInfo imageInfo, SessionStatus sessionStatus, Registration registration) {
        if (!validImage(imageInfo)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.sessionPeriod = sessionPeriod;
        this.coverImage = imageInfo;
        this.sessionStatus = sessionStatus;
        this.registration = registration;
    }

    private boolean validImage(ImageInfo imageInfo) {
        return imageInfo.smallerOrEqual(STANDARD_IMAGE_INFO)
                && imageInfo.widerOrEqual(STANDARD_IMAGE_INFO)
                && imageInfo.LongerOrEqual(STANDARD_IMAGE_INFO)
                && imageInfo.hasRatio(IMAGE_RATIO);
    }

    public Payment register(NsUser user, Long amount) {
        if (!sessionStatus.isOpened()) {
            throw new IllegalStateException();
        }
        registration.register(user, amount);
        return new Payment(makePaymentId(user), this.id, user.getId(), amount);
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
