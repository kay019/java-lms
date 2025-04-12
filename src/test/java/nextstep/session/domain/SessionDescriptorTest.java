package nextstep.session.domain;

import nextstep.session.domain.image.SessionImage;
import nextstep.session.domain.property.SessionProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionDescriptorTest {

    private SessionPeriod period;
    private SessionProperty property;

    @BeforeEach
    void setUp() {
        period = new SessionPeriod(LocalDateTime.now(), LocalDateTime.now());
        property = new SessionProperty();
    }

    @DisplayName("SessionDescriptor 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionDescriptor(new SessionImage(), period, property));
    }
}
