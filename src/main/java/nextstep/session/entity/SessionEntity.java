package nextstep.session.entity;

import java.time.LocalDate;
import java.util.List;

import nextstep.session.domain.Student;
import nextstep.session.mapper.StudentMapper;

import static java.util.stream.Collectors.toList;

public class SessionEntity {
    public static final String COL_ID = "id";
    public static final String COL_COURSE_ID = "course_id";
    public static final String COL_PROGRESS_STATUS = "progress_status";
    public static final String COL_ENROLLMENT_STATUS = "enrollment_status";
    public static final String COL_FEE = "fee";
    public static final String COL_CAPACITY = "capacity";
    public static final String COL_START_DATE = "start_date";
    public static final String COL_END_DATE = "end_date";

    private final long id;
    private final long courseId;
    private final String progressStatus;
    private final String enrollmentStatus;
    private final int fee;
    private final int capacity;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<StudentEntity> studentEntities;

    public SessionEntity(long id, long courseId, String progressStatus, String enrollmentStatus, int fee, int capacity, LocalDate startDate, LocalDate endDate, List<Student> students) {
        this.id = id;
        this.courseId = courseId;
        this.progressStatus = progressStatus;
        this.enrollmentStatus = enrollmentStatus;
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

    public String getProgressStatus() {
        return progressStatus;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
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
