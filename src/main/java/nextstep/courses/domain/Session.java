package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SessionState sessionState;
    private RegisterStrategy registerStrategy;
    private List<NsUser> students = new ArrayList<>();

    public Session(LocalDateTime startDate, LocalDateTime endDate, SessionState sessionState, RegisterStrategy registerStrategy) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessionState = sessionState;
        this.registerStrategy = registerStrategy;
    }
}
