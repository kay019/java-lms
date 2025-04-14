package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;

import java.util.List;

public class Register {
    public static NsStudent registerSession(NsUser user, Long sessionId, NaturalNumber money, SessionState sessionState, PayStrategy registerStrategy, List<NsStudent> students) {
        validateSessionState(sessionState);
        validateDuplicateUser(students, user);
        registerStrategy.pay(user, sessionId, students.size(), money);
        return new NsStudent(user, sessionId);
    }

    private static void validateSessionState(SessionState sessionState) {
        if (SessionState.canNotRegister(sessionState)) {
            throw new CannotRegisterException("강의는 모집 중일 때만 등록할 수 있습니다.");
        };
    }

    private static void validateDuplicateUser(List<NsStudent> students, NsUser user) {
        students.stream()
                .filter(u -> u.isSameUser(user))
                .findFirst()
                .ifPresent(u -> {
                    throw new CannotRegisterException("이미 이 강의에 등록한 사람입니다.");
                });
    }
}
