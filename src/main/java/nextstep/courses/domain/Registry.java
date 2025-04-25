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
    private SessionRecruitmentState sessionRecruitmentState;
    private SessionAccessType sessionAccessType;


    public Registry(PayStrategy payStrategy, SessionRecruitmentState sessionRecruitmentState, PositiveNumber capacity) {
        this(new ArrayList<>(), payStrategy, sessionRecruitmentState, capacity, SessionAccessType.OPEN);
    }

    public Registry(PayStrategy payStrategy, SessionRecruitmentState sessionRecruitmentState, PositiveNumber capacity, SessionAccessType sessionAccessType) {
        this(new ArrayList<>(), payStrategy, sessionRecruitmentState, capacity, sessionAccessType);
    }

    public Registry(List<NsStudent> students, PayStrategy payStrategy, SessionRecruitmentState sessionRecruitmentState, PositiveNumber capacity, SessionAccessType sessionAccessType) {
        this.payStrategy = payStrategy;
        this.sessionRecruitmentState = sessionRecruitmentState;
        this.capacity = capacity;
        this.students = new NsStudents(students);
        this.sessionAccessType = sessionAccessType;
    }

    public void register(NsUser user, Long sessionId, PositiveNumber money, PositiveNumber price) {
        validateSessionProgressState(sessionRecruitmentState);
        payStrategy.pay(user, sessionId, money, price);
        NsStudent student = new NsStudent(user.getId(), sessionId, sessionAccessType.getDefaultApplicationState());

        students.enroll(student, capacity);
    }

    private void validateSessionProgressState(SessionRecruitmentState sessionState) {
        if (sessionState.canNotRegister()) {
            throw new CannotRegisterException("강의는 모집 중일 때만 등록할 수 있습니다.");
        };
    }

    public void confirmStudent(NsUser user, ApplicationState applicationState) {
        validateSessionAccessType();
        students.changeStudentStatus(user, applicationState);
    }

    private void validateSessionAccessType() {
        if (sessionAccessType.isOpen()) {
            throw new IllegalArgumentException("누구나 수강 가능한 강의는 승인할 필요가 없습니다.");
        }
    }

    public String getSessionRecruitmentState() {
        return sessionRecruitmentState.name();
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

    public String getSessionAccessType() {
        return sessionAccessType.name();
    }
}
