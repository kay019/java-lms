package nextstep.common.domian;

import java.time.LocalDateTime;
import java.util.Objects;

public class BaseDomain {
    protected Long id;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    protected boolean deleted = false;

    public BaseDomain() {

    }

    public BaseDomain(Long id) {
        this(id, LocalDateTime.now(), LocalDateTime.now());
    }

    public BaseDomain(Long id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDomain that = (BaseDomain) o;
        return Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt);
    }
}
