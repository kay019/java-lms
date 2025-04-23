package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class Students {
    private final List<Student> students = new ArrayList<>();

    public void add(Student student) {
        this.students.add(student);
    }

    public int size() {
        return students.size();
    }
}
