package nextstep.common.domain;

import java.time.LocalDateTime;

public class Audit {
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    
    public Audit(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
