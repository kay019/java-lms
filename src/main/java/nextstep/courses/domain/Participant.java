package nextstep.courses.domain;

import java.util.Objects;

public class Participant {
    private Long userId;

    public Participant(Long userId) {
        this.userId = userId;
    }
    
    public Long getId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
