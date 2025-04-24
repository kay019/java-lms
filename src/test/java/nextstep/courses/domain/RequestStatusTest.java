package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestStatusTest {

    @ParameterizedTest
    @DisplayName("신청 상태가 아니면 수강 승인을 할 수 없다.")
    @EnumSource(value = RequestStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "REQUESTED")
    void validateApproveTest_notREQUESTED(RequestStatus input) {
        assertThatIllegalArgumentException()
                .isThrownBy(input::validateApprove);
    }

    @Test
    @DisplayName("신청 상태이면 수강 승인을 할 수 있다.")
    void validateApproveTest_REQUESTED() {
        RequestStatus status = RequestStatus.REQUESTED;

        assertDoesNotThrow(status::validateApprove);
    }

    @ParameterizedTest
    @DisplayName("신청 상태가 아니면 수강 거절을 할 수 없다.")
    @EnumSource(value = RequestStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "REQUESTED")
    void validateRejectTest_notREQUESTED(RequestStatus input) {
        assertThatIllegalArgumentException()
                .isThrownBy(input::validateReject);
    }

    @Test
    @DisplayName("신청 상태이면 수강 승인을 할 수 있다.")
    void validateRejectTest_REQUESTED() {
        RequestStatus status = RequestStatus.REQUESTED;

        assertDoesNotThrow(status::validateReject);
    }

}
