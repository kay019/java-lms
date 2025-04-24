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

    public Registry(List<NsStudent> students, PayStrategy payStrategy, String sessionRecruitmentState, Long capacity) {
        this(students, payStrategy, SessionRecruitmentState.valueOf(sessionRecruitmentState), new PositiveNumber(capacity));
    }

    public Registry(PayStrategy payStrategy, SessionRecruitmentState sessionRecruitmentState, PositiveNumber capacity) {
        this(new ArrayList<>(), payStrategy, sessionRecruitmentState, capacity);
    }

    public Registry(List<NsStudent> students, PayStrategy payStrategy, SessionRecruitmentState sessionRecruitmentState, PositiveNumber capacity) {
        this.payStrategy = payStrategy;
        this.sessionRecruitmentState = sessionRecruitmentState;
        this.capacity = capacity;
        this.students = new NsStudents(students);
    }

    public void register(NsUser user, Long sessionId, PositiveNumber money, PositiveNumber price) {
        validateSessionProgressState(sessionRecruitmentState);
        payStrategy.pay(user, sessionId, money, price);
        NsStudent student = new NsStudent(user, sessionId);
        students.enroll(student, capacity);
    }

    private void validateSessionProgressState(SessionRecruitmentState sessionState) {
        if (sessionState.canNotRegister()) {
            throw new CannotRegisterException("강의는 모집 중일 때만 등록할 수 있습니다.");
        };
    }

    public void confirmStudent(NsUser user, ApplicationState applicationState) {
        students.changeStudentStatus(user, applicationState);
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
}
