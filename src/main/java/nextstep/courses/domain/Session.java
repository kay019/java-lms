package nextstep.courses.domain;

import java.time.LocalDate;

public class Session {

    private final Image coverImage;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final boolean isFree;

    private Session(Image coverImage, LocalDate startDate, LocalDate endDate, boolean isFree) {
        validate(coverImage, startDate, endDate);

        this.coverImage = coverImage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFree = isFree;
    }

    public static Session createFreeSession(Image coverImage, LocalDate startDate, LocalDate endDate) {
        return new Session(coverImage, startDate, endDate, true);
    }

    public static Session createPaidSession(Image coverImage, LocalDate startDate, LocalDate endDate) {
        return new Session(coverImage, startDate, endDate, false);
    }

    private void validate(Image coverImage, LocalDate startAt, LocalDate endAt) {
        if (coverImage == null) {
            throw new IllegalArgumentException("커버 이미지는 필수 값입니다.");
        }

        if (startAt == null || endAt == null) {
            throw new IllegalArgumentException("시작일과 종료일은 필수 값입니다.");
        }

        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }

}
