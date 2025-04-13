package nextstep.courses.domain;

import java.time.LocalDate;
import java.util.List;

public class Session {
    private LocalDate startDate;
    private LocalDate endDate;
    private CoverImage coverImage;
    private SessionStatus status;
    private List<Student> students;
}
