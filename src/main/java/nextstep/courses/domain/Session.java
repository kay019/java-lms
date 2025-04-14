package nextstep.courses.domain;

import nextstep.courses.CannotCreateSessionException;
import nextstep.courses.CannotRegisterException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private Long id;
    private Period sessionPeriod;
    private SessionState sessionState;
    private PayStrategy registerStrategy;
    private Image coverImage;
    private List<NsStudent> students = new ArrayList<>();

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionState sessionState, PayStrategy registerStrategy, Image coverImage) {
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

    public NsStudent register(NsUser user, NaturalNumber money) {
        validateDuplicateUser(user);
        NsStudent student = Register.registerSession(user, id, money, sessionState, registerStrategy, students.size());
        students.add(student);
        return student;
    }

    private void validateDuplicateUser(NsUser user) {
        students.stream()
                .filter(u -> u.isSameUser(user))
                .findFirst()
                .ifPresent(u -> {
                    throw new CannotRegisterException("이미 이 강의에 등록한 사람입니다.");
                });
    }
}
