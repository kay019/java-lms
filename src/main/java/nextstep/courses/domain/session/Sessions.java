package nextstep.courses.domain.session;

import nextstep.courses.entity.SessionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sessions {
    private final List<Session> value;

    public Sessions() {
        this(new ArrayList<>());
    }

    public Sessions(List<Session> value) {
        this.value = value;
    }

    public boolean add(Session session) {
        return value.add(session);
    }

    public void delete() {
        value.forEach(Session::delete);
    }

    public List<SessionEntity> to(Long courseId) {
        return value.stream()
            .map(session -> session.to(courseId))
            .collect(Collectors.toList());
    }
}
