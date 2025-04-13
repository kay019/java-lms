package nextstep.sessions.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Session {
    private final ImageInfo STANDARD_IMAGE_INFO = new ImageInfo(10^6, "jpg", 300, 200);
    private final float IMAGE_RATIO = 1.5f;

    private final Period sessionPeriod;
    private final ImageInfo coverImage;

    public Session(LocalDate startDate, LocalDate endDate, ImageInfo imageInfo) {
        if (!validImage(imageInfo)) {
            throw new IllegalArgumentException();
        }
        this.sessionPeriod = new Period(startDate, endDate);
        this.coverImage = imageInfo;
    }

    public Session(Period sessionPeriod, ImageInfo imageInfo) {
        if (!validImage(imageInfo)) {
            throw new IllegalArgumentException();
        }
        this.sessionPeriod = sessionPeriod;
        this.coverImage = imageInfo;
    }

    private boolean validImage(ImageInfo imageInfo) {
        return imageInfo.smallerOrEqual(STANDARD_IMAGE_INFO)
                && imageInfo.widerOrEqual(STANDARD_IMAGE_INFO)
                && imageInfo.LongerOrEqual(STANDARD_IMAGE_INFO)
                && imageInfo.hasRatio(IMAGE_RATIO);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionPeriod, session.sessionPeriod) && Objects.equals(coverImage, session.coverImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionPeriod, coverImage);
    }
}
