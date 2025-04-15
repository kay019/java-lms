package nextstep.courses.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Session {

    private final Image coverImage;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final boolean isFree;

    private final Integer maxParticipants;

    private final Long price;

    private final List<Long> participants = new ArrayList<>();

    private Session(Image coverImage, LocalDate startDate, LocalDate endDate, boolean isFree, Integer maxParticipants, Long price) {
        validate(coverImage, startDate, endDate, isFree, maxParticipants, price);

        this.coverImage = coverImage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFree = isFree;
        this.maxParticipants = maxParticipants;
        this.price = price;
    }

    public static Session createFreeSession(Image coverImage, LocalDate startDate, LocalDate endDate) {
        return new Session(coverImage, startDate, endDate, true, null, null);
    }

    public static Session createPaidSession(Image coverImage, LocalDate startDate, LocalDate endDate, Integer maxParticipants, Long price) {
        return new Session(coverImage, startDate, endDate, false, maxParticipants, price);
    }

    private void validate(Image coverImage, LocalDate startAt, LocalDate endAt, boolean isFree, Integer maxParticipants, Long price) {
        if (coverImage == null) {
            throw new IllegalArgumentException("커버 이미지는 필수 값입니다.");
        }

        if (startAt == null || endAt == null) {
            throw new IllegalArgumentException("시작일과 종료일은 필수 값입니다.");
        }

        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }

        if (isFree && (maxParticipants != null || price != null)) {
            throw new IllegalArgumentException("무료 세션은 최대 참여자 수와 가격을 설정할 수 없습니다.");
        }

        if (!isFree && (maxParticipants == null || price == null)) {
            throw new IllegalArgumentException("유료 세션은 최대 참여자 수와 가격을 설정해야 합니다.");
        }

        if (maxParticipants != null && maxParticipants <= 0) {
            throw new IllegalArgumentException("최대 참여자 수는 1 이상이어야 합니다.");
        }

        if (price != null && price <= 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
    }

    public void enroll(Long userId, long amount) {
        validatePaidEnrollment(amount);
        participants.add(userId);
    }

    private void validatePaidEnrollment(long amount) {
        if (isFree) {
            return;
        }

        if (participants.size() >= maxParticipants) {
            throw new IllegalArgumentException("최대 참여자 수를 초과했습니다.");
        }
        
        if (amount != price) {
            throw new IllegalArgumentException("가격이 일치하지 않습니다.");
        }
    }

    public boolean isParticipant(Long userId) {
        return participants.contains(userId);
    }

}
