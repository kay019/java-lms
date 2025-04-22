package nextstep.session.entity;

import java.time.LocalDate;
import java.util.List;

import nextstep.enrollment.domain.Student;
import nextstep.enrollment.entity.StudentEntity;
import nextstep.enrollment.mapper.StudentMapper;

import static java.util.stream.Collectors.toList;

public class SessionEntity {
    public static final String COL_ID = "id";
    public static final String COL_COURSE_ID = "course_id";
    public static final String COL_STATUS = "status";
    public static final String COL_FEE = "fee";
    public static final String COL_CAPACITY = "capacity";
    public static final String COL_START_DATE = "start_date";
    public static final String COL_END_DATE = "end_date";

    private final long id;
    private final long courseId;
    private final String status;
    private final int fee;
    private final int capacity;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<StudentEntity> studentEntities;

    public SessionEntity(long id, long courseId, String status, int fee, int capacity, LocalDate startDate, LocalDate endDate, List<Student> students) {
        this.id = id;
        this.courseId = courseId;
        this.status = status;
        this.fee = fee;
        this.capacity = capacity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.studentEntities = toEntity(students);
    }

    private List<StudentEntity> toEntity(List<Student> students) {
        StudentMapper mapper = new StudentMapper();

        return students.stream()
                .map(mapper::toEntity)
                .collect(toList());
    }

    public long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getStatus() {
        return status;
    }

    public int getFee() {
        return fee;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<StudentEntity> getStudentEntities() {
        return studentEntities;
    }
}
