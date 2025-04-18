package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.courses.PayStrategyFactory;
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

    public Registry(List<NsStudent> students, PayStrategy payStrategy, String sessionState, Long capacity) {
        this(students, payStrategy, SessionState.valueOf(sessionState), new PositiveNumber(capacity));
    }

    public Registry(PayStrategy payStrategy, SessionState sessionState, PositiveNumber capacity) {
        this(new ArrayList<>(), payStrategy, sessionState, capacity);
    }

    public Registry(List<NsStudent> students, PayStrategy payStrategy, SessionState sessionState, PositiveNumber capacity) {
        this.payStrategy = payStrategy;
        this.sessionState = sessionState;
        this.capacity = capacity;
        this.students = new NsStudents(students);
    }

    public void register(NsUser user, Long sessionId, PositiveNumber money, PositiveNumber price) {
        validateSessionState(sessionState);
        payStrategy.pay(user, sessionId, money, price);
        NsStudent student = new NsStudent(user, sessionId);
        students.enroll(student, capacity);
    }

    private void validateSessionState(SessionState sessionState) {
        if (SessionState.canNotRegister(sessionState)) {
            throw new CannotRegisterException("강의는 모집 중일 때만 등록할 수 있습니다.");
        };
    }

    public String getSessionState() {
        return sessionState.name();
    }

    public String getPayStrategy() {
        return payStrategy.getType();
    }

    public Long getCapacity() {
        return capacity.value();
    }

    public List<NsStudent> getStudents() {
        return students.getStudents();
    }
}
