package nextstep.sessions.domain;

import java.util.List;
import java.util.Objects;
import nextstep.users.domain.NsUser;

public class Students {
    private final Long capacity;
    private final List<Student> students;

    public Students(Long capacity, List<Student> students) {
        this.capacity = capacity;
        this.students = students;
    }

    public void enroll(NsUser nsUser) {
        if (isFullCapacity()) {
            throw new IllegalStateException("capacity is full");
        }

        if (isAlreadyEnrolled(nsUser)) {
            throw new IllegalStateException("already enrolled student");
        }
    }

    private boolean isFullCapacity() {
        return getStudentsSize() >= capacity;
    }

    private int getStudentsSize() {
        return students.size();
    }

    private boolean isAlreadyEnrolled(NsUser nsUser) {
        return students.stream()
                .anyMatch(student -> student.isSameUser(nsUser.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Students students1 = (Students) o;
        return capacity == students1.capacity && Objects.equals(students, students1.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, students);
    }
}
