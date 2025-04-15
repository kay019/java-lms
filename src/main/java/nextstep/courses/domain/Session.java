package nextstep.courses.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Session {

    private final Image coverImage;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final List<Long> participants = new ArrayList<>();

    protected Session(Image coverImage, LocalDate startDate, LocalDate endDate) {
        validate(coverImage, startDate, endDate);

        this.coverImage = coverImage;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public void enroll(Long userId, long amount) {
        validateEnrollment(amount);
        participants.add(userId);
    }

    protected abstract void validateEnrollment(long amount);

    protected int getParticipantsCount() {
        return participants.size();
    }

    public boolean isParticipant(Long userId) {
        return participants.contains(userId);
    }

}
