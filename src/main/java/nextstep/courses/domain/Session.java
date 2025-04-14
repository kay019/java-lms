package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private Long id;
    private Period sessionPeriod;
    private SessionState sessionState;
    private RegisterStrategy registerStrategy;
    private Image coverImage;
    private List<NsUser> students = new ArrayList<>();

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionState sessionState, RegisterStrategy registerStrategy, Image coverImage) {
        this.id = id;
        this.sessionPeriod = new Period(startDate, endDate);
        this.sessionState = sessionState;
        this.registerStrategy = registerStrategy;
        this.coverImage = coverImage;
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new CannotCreateSessionException("강의의 시작 날짜가 끝나는 날짜보다 뒤입니다.");
        }
    }

    public Payment register(NsUser user, NaturalNumber money) {
        if (SessionState.canNotRegister(sessionState)) {
            throw new CannotRegisterException("강의는 모집 중일 때만 등록할 수 있습니다.");
        }
        validateUser(user);
        students.add(user);
        return registerStrategy.register(user, id, students.size(), money);
    }

    private void validateUser(NsUser user) {
        students.stream()
                .filter(u -> u.matchUser(user))
                .findFirst()
                .ifPresent(u -> {
                    throw new CannotRegisterException("이미 이 강의에 등록한 사람입니다.");
                });
    }
}
