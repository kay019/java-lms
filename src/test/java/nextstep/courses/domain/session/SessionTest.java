package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import java.util.stream.Stream;
import nextstep.courses.domain.session.inner.EnrollmentStatus;
import nextstep.courses.domain.session.inner.FreeSessionType;
import nextstep.courses.domain.session.inner.PaidSessionType;
import nextstep.courses.domain.session.inner.SessionStatus;
import nextstep.payments.domain.Payment;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SessionTest {
    public static final Session SESSION1 = new Session(1L, 1L, "free1", new FreeSessionType(), LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31), SessionStatus.PREPARING, EnrollmentStatus.RECRUITING, 0);
    public static final Session SESSION2 = new Session(2L, 1L, "paid1", new PaidSessionType(10, 10000), LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31), SessionStatus.IN_PROGRESS, EnrollmentStatus.RECRUITING, 0);
    public static final Session SESSION3 = new Session(3L, 1L, "free2", new FreeSessionType(), LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31), SessionStatus.COMPLETED, EnrollmentStatus.NOT_RECRUITING, 0);
    public static final Session SESSION4 = new Session(1L, 1L, "free3", new FreeSessionType(), LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31), SessionStatus.PREPARING, EnrollmentStatus.NOT_RECRUITING, 0);

    @ParameterizedTest
    @MethodSource("수강신청_예상")
    void 수강신청상태(Session session, boolean expected) {
        boolean result = session.enroll(new Payment());
        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> 수강신청_예상() {
        return Stream.of(
                Arguments.of(SESSION1, true),
                Arguments.of(SESSION3, false),
                Arguments.of(SESSION4, false)
        );
    }

}
