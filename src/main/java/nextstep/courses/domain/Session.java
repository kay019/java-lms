package nextstep.courses.domain;

import nextstep.courses.CannotCreateSessionException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private Long id;
    private Period sessionPeriod;
    private Image coverImage;
    private Registry registry;

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionState sessionState, PayStrategy payStrategy, Image coverImage, Long capacity) {
        this.id = id;
        this.sessionPeriod = new Period(startDate, endDate);
        this.coverImage = coverImage;
        this.registry = new Registry(payStrategy, sessionState, new PositiveNumber(capacity));
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new CannotCreateSessionException("강의의 시작 날짜가 끝나는 날짜보다 뒤입니다.");
        }
    }

    public void register(NsUser user, PositiveNumber money) {
        registry.register(user, id, money);
    }
}
