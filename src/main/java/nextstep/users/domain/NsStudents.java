package nextstep.users.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.courses.domain.ApplicationState;
import nextstep.courses.domain.PositiveNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NsStudents {
    private final List<NsStudent> students;

    public NsStudents() {
        this(new ArrayList<>());
    }

    public NsStudents(NsStudent student) {
        this(Arrays.asList(student));
    }

    public NsStudents(List<NsStudent> students) {
        this.students = students;
    }

    public void enroll(NsStudent student, PositiveNumber capacity) {
        validateDuplicateUser(student);
        validateCapacity(capacity);
        students.add(student);
    }

    private void validateDuplicateUser(NsStudent student) {
        students.stream()
                .filter(u -> u.equals(student))
                .findFirst()
                .ifPresent(u -> {
                    throw new CannotRegisterException("이미 수강생입니다.");
                });
    }

    private void validateCapacity(PositiveNumber capacity) {
        if (capacity.compareTo(students.size()) >= 0) {
            throw new CannotRegisterException("최대 수강 인원을 초과하였습니다.");
        }
    }

    public void changeStudentStatus(NsUser user, ApplicationState applicationState) {
        for (NsStudent nsStudent: students) {
            if (!nsStudent.isSameUser(user)) {
                continue;
            }
            nsStudent.changeStatus(applicationState);
            return;
        }
        throw new IllegalArgumentException("해당 학생이 존재하지 않습니다.");
    }

    public List<NsStudent> getStudents() {
        return students;
    }
}
