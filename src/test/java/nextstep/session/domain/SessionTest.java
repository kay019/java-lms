package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseTest;
import nextstep.payments.domain.Payment;
import nextstep.session.domain.image.SessionCoverImage;
import nextstep.session.domain.image.SessionCoverImageTest;
import nextstep.session.domain.image.SessionCoverImageType;
import nextstep.session.domain.payment.SessionCapacity;
import nextstep.session.domain.payment.SessionFee;
import nextstep.session.domain.payment.SessionPayments;
import nextstep.session.domain.payment.SessionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static nextstep.session.domain.image.RowsTest.dummyRows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {

    private Course course;
    private SessionPayments payments;
    private SessionCoverImage image;
    private SessionPeriod period;

    @BeforeEach
    void setUp() {
        course = new Course("test-course-1", 1L);
        payments = new SessionPayments(SessionType.FREE, new SessionFee(500_000), new SessionCapacity(80));
        image = new SessionCoverImage(dummyRows(300, 200), SessionCoverImageType.GIF);
        period = new SessionPeriod(LocalDateTime.now(), LocalDateTime.now());
    }

    @DisplayName("Session 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Session(1L, course, image, payments, period));
    }

}
