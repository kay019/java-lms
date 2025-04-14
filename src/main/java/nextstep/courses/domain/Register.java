package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;

public class Register {
    public static NsStudent registerSession(NsUser user, Long sessionId, NaturalNumber money, SessionState sessionState, PayStrategy registerStrategy, int studentCount) {
        validateSessionState(sessionState);
        registerStrategy.pay(user, sessionId, studentCount, money);
        return new NsStudent(user, sessionId);
    }

    private static void validateSessionState(SessionState sessionState) {
        if (SessionState.canNotRegister(sessionState)) {
            throw new CannotRegisterException("강의는 모집 중일 때만 등록할 수 있습니다.");
        };
    }
}
