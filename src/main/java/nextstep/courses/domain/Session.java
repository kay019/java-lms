package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SessionState sessionState;
    private RegisterStrategy registerStrategy;
    private List<NsUser> students = new ArrayList<>();

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionState sessionState, RegisterStrategy registerStrategy) {
        validateDate(startDate, endDate);
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessionState = sessionState;
        this.registerStrategy = registerStrategy;
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new CannotCreateSessionException("강의의 시작 날짜가 끝나는 날짜보다 뒤입니다.");
        }
    }

    public Payment register(NsUser user, Long money) {
        return registerStrategy.register(user, id, money);
    }

}
