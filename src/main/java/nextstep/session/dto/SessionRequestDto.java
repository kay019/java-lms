package nextstep.session.dto;

import java.time.LocalDateTime;
import java.util.List;
import nextstep.session.domain.SessionStatus;
import nextstep.session.domain.Student;
import nextstep.session.domain.cover.SessionCover;
import nextstep.session.domain.type.SessionType;

public class SessionRequestDto {
    private Long id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private SessionCover cover;
    private SessionType sessionType;
    private SessionStatus sessionStatus;
    private Long capacity;
    private List<Student> students;

    public SessionRequestDto(Long id, LocalDateTime startAt, LocalDateTime endAt, SessionCover cover,
                             SessionType sessionType, SessionStatus sessionStatus, Long capacity,
                             List<Student> students) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.cover = cover;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.capacity = capacity;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public SessionCover getCover() {
        return cover;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public Long getCapacity() {
        return capacity;
    }

    public List<Student> getStudents() {
        return students;
    }
}
