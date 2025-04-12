package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class SessionDateTest {

    @Test
    void 강의날짜(){
        SessionDate sessionDate = new SessionDate(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31));

        assertThat(sessionDate.startDate()).isEqualTo(LocalDate.of(2023, 10, 1));
        assertThat(sessionDate.finishDate()).isEqualTo(LocalDate.of(2023, 10, 31));
    }

    @Test
    void 강의날짜_범위(){
        assertThatThrownBy(() -> new SessionDate(LocalDate.of(2023, 10, 11), LocalDate.of(2023, 10, 1))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일보다 뒤에 있어야 합니다.");

        assertThatThrownBy(() -> new SessionDate(null, LocalDate.of(2023, 10, 1))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일보다 뒤에 있어야 합니다.");

        assertThatThrownBy(() -> new SessionDate(LocalDate.of(2023, 10, 11), null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일보다 뒤에 있어야 합니다.");

        assertThatThrownBy(() -> new SessionDate(null, null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일보다 뒤에 있어야 합니다.");

    }

}
