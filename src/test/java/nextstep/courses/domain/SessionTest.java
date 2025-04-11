package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SessionTest {

    @Test
    void 강의날짜(){
        Session session = new Session(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31));

        assertThat(session.startDate()).isEqualTo(LocalDate.of(2023, 10, 1));
        assertThat(session.finishDate()).isEqualTo(LocalDate.of(2023, 10, 31));
    }

    @Test
    void 강의날짜_범위(){
        assertThatThrownBy(() -> new Session(LocalDate.of(2023, 10, 11), LocalDate.of(2023, 10, 1))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일보다 뒤에 있어야 합니다.");

        assertThatThrownBy(() -> new Session(null, LocalDate.of(2023, 10, 1))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일보다 뒤에 있어야 합니다.");

        assertThatThrownBy(() -> new Session(LocalDate.of(2023, 10, 11), null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일보다 뒤에 있어야 합니다.");

        assertThatThrownBy(() -> new Session(null, null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일보다 뒤에 있어야 합니다.");

    }

}
