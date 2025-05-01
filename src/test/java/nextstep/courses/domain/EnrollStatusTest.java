package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EnrollStatusTest {
    @ParameterizedTest
    @DisplayName("강의 수강신청은 강의 상태가 모집중일 때가 아니면 예외를 발생한다.")
    @EnumSource(value = EnrollStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "RECRUITING")
    void validateEnrollTest_notRECRUITING(EnrollStatus input) {
        assertThatIllegalArgumentException()
                .isThrownBy(input::validateEnroll);
    }

    @Test
    @DisplayName("강의 수강신청은 강의 상태가 모집중일 때만 가능하다.")
    void validateEnrollTest_RECRUITING() {
        EnrollStatus status = EnrollStatus.RECRUITING;

        assertDoesNotThrow(status::validateEnroll);
    }

}
