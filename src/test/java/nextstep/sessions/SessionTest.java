package nextstep.sessions;

import nextstep.sessions.domain.ImageInfo;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.Period;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {
    @Test
    void testCreateSession() {
        Period sessionPeriod = new Period(
                LocalDate.of(2025, 4, 13),
                LocalDate.of(2025, 4, 14)
        );
        ImageInfo imageInfo = new ImageInfo(10^6, "png", 300, 200);
        assertThat(new Session(sessionPeriod, imageInfo)).isEqualTo(new Session(sessionPeriod, imageInfo));
    }

    @Test
    void testInvalidImageVolume() {
        ImageInfo imageInfo = new ImageInfo(10^7, "png", 300, 200);
        assertThatThrownBy(() ->
            new Session(LocalDate.of(2025, 4, 13),
                    LocalDate.of(2025, 4, 14),
                    imageInfo
            )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidImageWidth() {
        ImageInfo imageInfo = new ImageInfo(10^6, "jpg", 100, 200);
        assertThatThrownBy(() ->
                new Session(LocalDate.of(2025, 4, 13),
                        LocalDate.of(2025, 4, 14),
                        imageInfo
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidImageHeight() {
        ImageInfo imageInfo = new ImageInfo(10^6, "jpg", 300, 100);
        assertThatThrownBy(() ->
                new Session(LocalDate.of(2025, 4, 13),
                        LocalDate.of(2025, 4, 14),
                        imageInfo
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidImageRatio() {
        ImageInfo imageInfo = new ImageInfo(10^6, "jpg", 400, 200);
        assertThatThrownBy(() ->
                new Session(LocalDate.of(2025, 4, 13),
                        LocalDate.of(2025, 4, 14),
                        imageInfo
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
