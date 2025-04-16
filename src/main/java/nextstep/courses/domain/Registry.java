package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsStudents;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Registry {
    private NsStudents students;
    private PositiveNumber capacity;
    private PayStrategy payStrategy;
    private SessionState sessionState;

    public Registry(PayStrategy payStrategy, SessionState sessionState, PositiveNumber capacity) {
        this(new ArrayList<>(), payStrategy, sessionState, capacity);
    }

    public Registry(List<NsStudent> students, PayStrategy payStrategy, SessionState sessionState, PositiveNumber capacity) {
        this.payStrategy = payStrategy;
        this.sessionState = sessionState;
        this.capacity = capacity;
        this.students = new NsStudents(students);
    }

    public static NsStudent registerSession(NsUser user, Long sessionId, PositiveNumber money, SessionState sessionState, PayStrategy payStrategy, List<NsStudent> students) {
        validateSessionState(sessionState);
        payStrategy.pay(user, sessionId, money);
        return new NsStudent(user.getId(), sessionId);
    }

    public void register(NsUser user, Long sessionId, PositiveNumber money) {
        validateSessionState(sessionState);
        payStrategy.pay(user, sessionId, money);
        NsStudent student = new NsStudent(user, sessionId);
        students.enroll(student, capacity);
    }

    private static void validateSessionState(SessionState sessionState) {
        if (SessionState.canNotRegister(sessionState)) {
            throw new CannotRegisterException("강의는 모집 중일 때만 등록할 수 있습니다.");
        };
    }
}
