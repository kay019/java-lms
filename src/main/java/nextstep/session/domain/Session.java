package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.session.domain.image.SessionCoverImage;
import nextstep.session.domain.type.SessionType;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session {

    private Long id;

    private Course course;

    private SessionCoverImage coverSessionCoverImage;

    private int capacity;

    private Long fee;

    private List<Payment> payments;

    private SessionType sessionType;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public Session(Long id, Course course, SessionCoverImage sessionCoverImage, int capacity, Long fee, SessionType sessionType, LocalDateTime startDate, LocalDateTime endDate) {
        this(id, course, sessionCoverImage, capacity, fee, new ArrayList<>(), sessionType, startDate, endDate, LocalDateTime.now(), LocalDateTime.now());
    }

    public Session(Long id, Course course, SessionCoverImage sessionCoverImage, int capacity, Long fee, List<Payment> payments, SessionType sessionType, LocalDateTime startDate, LocalDateTime endDate) {
        this(id, course, sessionCoverImage, capacity, fee, payments, sessionType, startDate, endDate, LocalDateTime.now(), LocalDateTime.now());
    }

    public Session(Long id, Course course, SessionCoverImage sessionCoverImage, int capacity, Long fee, List<Payment> payments, SessionType sessionType, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.course = course;
        this.coverSessionCoverImage = sessionCoverImage;
        this.capacity = capacity;
        this.fee = fee;
        this.payments = payments;
        this.sessionType = sessionType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void toCourse(Course course) {
        this.course = course;
    }

    public boolean isCapacityNotExceeded() {
        return payments.size() < capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(course, session.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course);
    }
}
