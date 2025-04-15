package nextstep.courses.domain;

import java.time.LocalDate;

public class Session {
    private final Long id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final CoverImage coverImage;
    private final SessionStatus status;
    private final SessionType type;
    private final Integer capacity;
    private final Integer fee;
    private int studentsCount;

    public Session(LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionStatus status) {
        this(0L, startDate, endDate, coverImage, status, SessionType.FREE, null, null);
    }

    public Session(Long id, LocalDate startDate, LocalDate endDate, CoverImage coverImage, SessionStatus status, SessionType type, Integer capacity, Integer fee) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImage = coverImage;
        this.status = status;
        this.type = type;
        this.capacity = capacity;
        this.fee = fee;
        this.studentsCount = 0;
    }

    public void validateEnrollment(Long studentBudget) {
        if (status != SessionStatus.OPEN) {
            throw new IllegalArgumentException("세션이 '모집 중' 일때만 수강 신청이 가능합니다.");
        }
        if (type == SessionType.PAID) {
            if (studentsCount == capacity) {
                throw new IllegalArgumentException("정원이 초과되었습니다.");
            }
            if (fee > studentBudget) {
                throw new IllegalArgumentException("예산이 부족하여 강의를 신청할 수 없습니다.");
            }
        }
    }

    public void addStudent() {
        studentsCount++;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public SessionType getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getFee() {
        return fee;
    }

    public int getStudentsCount() {
        return studentsCount;
    }
}
