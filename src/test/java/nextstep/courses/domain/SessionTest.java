package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionTest {
    @Test
    @DisplayName("세션은 시작일과 종료일을 가집니다.")
    void createSession() {
        Session session = new Session(new Image(), LocalDate.now(), LocalDate.now());
        assertThat(session).isNotNull();
    }

    @Test
    @DisplayName("시작일과 종료일은 필수 값입니다.")
    void startDateAndEndDateMustBeRequired() {
        assertThatThrownBy(() -> {
            new Session(new Image(), null, LocalDate.now().plusDays(1));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시작일은 종료일 이전이어야 합니다.")
    void startDateMustBeBeforeEndDate() {
        assertThatThrownBy(() -> {
            new Session(new Image(), LocalDate.now().plusDays(1), LocalDate.now());
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("강의는 커버 이미지가 있어야 한다.")
    void courseMustHaveCoverImage() {
        assertThatThrownBy(() -> {
            new Session(null, LocalDate.now(), LocalDate.now());
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
