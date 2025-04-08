package nextstep.courses.infrastructure;

import nextstep.courses.record.CoverImageRecord;
import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.CoverImageTest;
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
        CoverImageRecord coverImageRecord = CoverImageRecord.from(coverImage);
        int count = jdbcCoverImageRepository.save(coverImageRecord);

        CoverImageRecord newCoverImageRecord = jdbcCoverImageRepository.findById(coverImageRecord.getId());
        CoverImage newCoverImage = newCoverImageRecord.toCoverImage();

        assertThat(count).isEqualTo(1);
        assertThat(coverImage.getSize()).isEqualTo(newCoverImage.getSize());
        assertThat(coverImage.getType()).isEqualTo(newCoverImage.getType());
        assertThat(coverImage.getWidth()).isEqualTo(newCoverImage.getWidth());
        assertThat(coverImage.getHeight()).isEqualTo(newCoverImage.getHeight());
    }

}
