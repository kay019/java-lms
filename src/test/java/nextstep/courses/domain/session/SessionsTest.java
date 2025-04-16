package nextstep.courses.domain.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionsTest {

    @DisplayName("Sessions 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Sessions(List.of(new Session())));
    }
}
