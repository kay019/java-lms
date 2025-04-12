package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseTest;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentTest;
import nextstep.session.domain.image.SessionCoverImageTest;
import nextstep.session.domain.type.SessionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {

    @DisplayName("Session 인스턴스 생성")
    @Test
    public void testConstructor() {
        LocalDateTime now = LocalDateTime.now();
        assertDoesNotThrow(() -> new Session(
            1L,
            new Course(),
            SessionCoverImageTest.I1,
            80,
            200_000L,
            List.of(new Payment()),
            SessionType.PAID,
            now,
            now,
            now,
            now
        ));

        assertThatThrownBy(() ->  new Session(
            1L,
            new Course(),
            SessionCoverImageTest.I1,
            80,
            200_000L,
            List.of(new Payment()),
            SessionType.PAID,
            now,
            now,
            now,
            now
        ));
    }

    @DisplayName("Course 등록")
    @Test
    public void testToCourse() {
        Session session = new Session(
            1L,
            new Course(),
            SessionCoverImageTest.I1,
            80,
            200_000L,
            List.of(new Payment()),
            SessionType.PAID,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        session.toCourse(CourseTest.C1);
        assertThat(session).isEqualTo(new Session(
            1L,
            CourseTest.C1,
            SessionCoverImageTest.I1,
            80,
            200_000L,
            List.of(new Payment()),
            SessionType.PAID,
            LocalDateTime.now(),
            LocalDateTime.now()
        ));
    }
}
