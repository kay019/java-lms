package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class SessionTest {
    @Test
    void session_addStudent_테스트() {
        Session session = new Session(LocalDate.now(), LocalDate.now().plusDays(1), null, SessionStatus.READY);
        assertThat(session.getStudentsCount()).isEqualTo(0);
        session.addStudent();
        assertThat(session.getStudentsCount()).isEqualTo(1);
    }
}