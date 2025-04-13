package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Metadata {

    private final LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedAt;

    public Metadata() {
        this(LocalDateTime.now());
    }

    public Metadata(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void update() {
        this.updatedDate = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Metadata metadata = (Metadata) o;
        return Objects.equals(createdDate, metadata.createdDate) && Objects.equals(updatedDate, metadata.updatedDate) && Objects.equals(deletedAt,
            metadata.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, updatedDate, deletedAt);
    }

    @Override
    public String toString() {
        return "Metadata{" + "createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", deletedAt=" + deletedAt + '}';
    }
}
