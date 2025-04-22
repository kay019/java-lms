package nextstep.courses.infrastructure.session.inner;

import static nextstep.courses.domain.session.inner.CoverImageTest.COVER_IMAGE1;
import static org.assertj.core.api.Assertions.assertThat;

import nextstep.courses.domain.session.inner.CoverImage;
import nextstep.courses.domain.session.inner.CoverImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CoverImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoverImageRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CoverImageRepository coverImageRepository;

    @BeforeEach
    void setUp() {
        coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        CoverImage coverImage = COVER_IMAGE1;
        int count = coverImageRepository.save(COVER_IMAGE1);
        assertThat(count).isEqualTo(1);
        CoverImage savedImage = coverImageRepository.findById(1L);
        assertThat(coverImage.getUrl()).isEqualTo(savedImage.getUrl());
        assertThat(coverImage.getWidth()).isEqualTo(savedImage.getWidth());
        assertThat(coverImage.getHeight()).isEqualTo(savedImage.getHeight());
        assertThat(coverImage.getSize()).isEqualTo(savedImage.getSize());
        LOGGER.debug("coverImage: {}", savedImage);
    }
}
