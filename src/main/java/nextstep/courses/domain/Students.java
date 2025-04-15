package nextstep.courses.domain;

import nextstep.students.domain.Student;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Students {
    private final List<Student> values;

    public Students(List<Student> students) {
        this.values = students;
    }

    public Students addStudent(Student student) {
        return new Students(Stream.concat(values.stream(), Stream.of(student)).collect(Collectors.toList()));
    }

    public int getSize() {
        return values.size();
    }
}
