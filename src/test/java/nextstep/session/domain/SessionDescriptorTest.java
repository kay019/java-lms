package nextstep.session.domain;

import nextstep.session.domain.image.SessionCoverImage;
import nextstep.session.domain.image.SessionCoverImageType;
import nextstep.session.domain.property.SessionProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static nextstep.session.domain.image.RowsTest.dummyRows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionDescriptorTest {

    private SessionCoverImage image;
    private SessionPeriod period;
    private SessionProperty property;

    @BeforeEach
    void setUp() {
        image = new SessionCoverImage(dummyRows(300, 200), SessionCoverImageType.GIF);
        period = new SessionPeriod(LocalDateTime.now(), LocalDateTime.now());
        property = new SessionProperty();
    }

    @DisplayName("SessionDescriptor 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionDescriptor(image, period, property));
    }
}
