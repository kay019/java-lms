package nextstep.courses.infrastructure;

import nextstep.courses.domain.CoverImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcCoverImageDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO session (id, start_date, end_date, phase, recruit_status, pricing_type, participant_type, capacity, fee) " +
                "VALUES (1, '2025-04-24', '2025-04-30', 'READY', 'OPEN', 'PAID', 'NORMAL', 30, 10000)");
        jdbcTemplate.update("INSERT INTO cover_image (session_id, size, type, width, height)" +
                " VALUES (1, 1024, 'png', 900, 600), (1, 2048, 'jpg', 600, 400)");
    }

    @Test
    void findBySessionId() {
        Long sessionId = 1L;
        JdbcCoverImageDao jdbcCoverImageDao = new JdbcCoverImageDao(jdbcTemplate);
        List<CoverImage> coverImages = jdbcCoverImageDao.findBySessionId(sessionId);

        // then
        assertThat(coverImages).hasSize(2);
        assertThat(coverImages.get(0).getSize()).isEqualTo(1024);
        assertThat(coverImages.get(0).getType()).isEqualTo("png");
        assertThat(coverImages.get(0).getWidth()).isEqualTo(900);
        assertThat(coverImages.get(0).getHeight()).isEqualTo(600);
        assertThat(coverImages.get(1).getSize()).isEqualTo(2048);
        assertThat(coverImages.get(1).getType()).isEqualTo("jpg");
        assertThat(coverImages.get(1).getWidth()).isEqualTo(600);
        assertThat(coverImages.get(1).getHeight()).isEqualTo(400);
    }

}
