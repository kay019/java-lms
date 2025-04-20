package nextstep.courses;

public class RegistryDto {
    private String payStrategy;
    private String sessionState;
    private Long capacity;

    public RegistryDto(String payStrategy, String sessionState, Long capacity) {
        this.payStrategy = payStrategy;
        this.sessionState = sessionState;
        this.capacity = capacity;
    }

    public String getPayStrategy() {
        return payStrategy;
    }

    public String getSessionState() {
        return sessionState;
    }

    public Long getCapacity() {
        return capacity;
    }
}
