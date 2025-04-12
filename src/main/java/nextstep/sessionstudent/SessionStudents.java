package nextstep.sessionstudent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SessionStudents {

    private final List<SessionStudent> sessionStudents;

    public SessionStudents() {
        this(new ArrayList<>());
    }

    public SessionStudents(List<SessionStudent> sessionStudents) {
        this.sessionStudents = sessionStudents;
    }

    public List<SessionStudent> getSessionStudents() {
        return sessionStudents;
    }

    public SessionStudents filterApproved() {
        List<SessionStudent> approvedStudents = sessionStudents
            .stream()
            .filter(SessionStudent::isApproved)
            .collect(Collectors.toList());
        return new SessionStudents(approvedStudents);
    }

    public int getSize() {
        return sessionStudents.size();
    }
}
