package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class CourseSession {
    private CompositeKey id;
    private SessionCoverImage image;
    private SessionType type;
    private SessionStatus status;

    private String title;

    private SessionPeriod period;
    private List<Long> participantIds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CourseSession(Long courseId, SessionCoverImage image, SessionType type, SessionStatus status, String title, SessionPeriod period) {
        this.id = new CompositeKey(0L, courseId);
        this.image = image;
        this.type = type;
        this.status = status;
        this.title = title;
        this.period = period;
        this.participantIds = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public Long register(Long nsUserId) {
        return register(nsUserId, null);
    }

    public Long register(Long nsUserId, Payment payment) {
        if (status != SessionStatus.PREPARING) {
            throw new InvalidParameterException("The registration period is not open.");
        }

        checkPossibleToRegister(nsUserId, payment);

        if (participantIds.contains(nsUserId)) {
            throw new InvalidParameterException("This user has already enrolled in the course.");
        }

        participantIds.add(nsUserId);

        return nsUserId;
    }

    public CompositeKey getId() {
        return id;
    }

    public int registeredUserSize() {
        return participantIds.size();
    }

    abstract void checkPossibleToRegister(Long nsUserId, Payment payment);

    public static class CompositeKey {
        private final Long id;
        private final Long courseId;

        public CompositeKey(Long id, Long courseId) {
            this.id = id;
            this.courseId = courseId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CompositeKey that = (CompositeKey) o;
            return Objects.equals(id, that.id) && Objects.equals(courseId, that.courseId);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(id);
            result = 31 * result + Objects.hashCode(courseId);
            return result;
        }
    }
}
