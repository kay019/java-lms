package nextstep.session.infrastructure;

import nextstep.courses.infrastructure.JdbcCoverImageRepository;
import nextstep.session.domain.CoverImage;
import nextstep.session.domain.CoverImageTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CoverImageRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcCoverImageRepository jdbcCoverImageRepository;

    @BeforeEach
    void setUp() {
        jdbcCoverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        CoverImage coverImage = CoverImageTest.createCoverImage1();
        int count = jdbcCoverImageRepository.save(coverImage);

        CoverImage newCoverImage = jdbcCoverImageRepository.findById(coverImage.getId());

        assertThat(count).isEqualTo(1);
        assertThat(coverImage.getSize()).isEqualTo(newCoverImage.getSize());
        assertThat(coverImage.getType()).isEqualTo(newCoverImage.getType());
        assertThat(coverImage.getWidth()).isEqualTo(newCoverImage.getWidth());
        assertThat(coverImage.getHeight()).isEqualTo(newCoverImage.getHeight());
    }

}
