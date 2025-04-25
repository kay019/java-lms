package nextstep.session.domain;

public enum ManagerAuthority {
    MANAGER,
    ASSISTANT;

    public boolean isManager() {
        return this == MANAGER;
    }
}
