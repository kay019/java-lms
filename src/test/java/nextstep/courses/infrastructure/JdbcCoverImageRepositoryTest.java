package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.CoverImage;
import nextstep.courses.domain.image.CoverImageExtension;
import nextstep.courses.domain.image.CoverImageFileSize;
import nextstep.courses.domain.image.CoverImagePixelSize;
import nextstep.courses.domain.repository.CoverImageRepository;
import nextstep.support.fixture.CoverImageFileSizeFixture;
import nextstep.support.fixture.CoverImagePixelSizeFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql(scripts = "/reset-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class JdbcCoverImageRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CoverImageRepository coverImageRepository;

    @BeforeEach
    void setUp() {
        coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
    }

    @Test
    void saveTest() {
        // given
        Long sessionId = 1L;
        CoverImageFileSize fileSize = CoverImageFileSizeFixture.create();
        CoverImageExtension extension = CoverImageExtension.JPEG;
        CoverImagePixelSize pixelSize = CoverImagePixelSizeFixture.create();
        CoverImage coverImage = new CoverImage(fileSize, extension, pixelSize);

        // when
        Long result = coverImageRepository.save(coverImage, sessionId);

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findBySessionIdTest_found() {
        // given
        Long sessionId = 1L;
        CoverImageFileSize fileSize = CoverImageFileSizeFixture.create();
        CoverImageExtension extension = CoverImageExtension.JPEG;
        CoverImagePixelSize pixelSize = CoverImagePixelSizeFixture.create();
        CoverImage coverImage = new CoverImage(fileSize, extension, pixelSize);
        coverImageRepository.save(coverImage, sessionId);
        coverImageRepository.save(coverImage, sessionId);

        // when
        List<CoverImage> result = coverImageRepository.findByAllSessionId(sessionId);

        // then
        assertThat(result).hasSize(2);
        for (int i = 0; i < result.size(); i++) {
            CoverImage image = result.get(i);
            assertThat(image.getFileSize().getSize()).isEqualTo(fileSize.getSize());
            assertThat(image.getExtension()).isEqualTo(CoverImageExtension.JPEG);
            assertThat(image.getPixelSize().getWidth()).isEqualTo(pixelSize.getWidth());
            assertThat(image.getPixelSize().getHeight()).isEqualTo(pixelSize.getHeight());
        }
    }

    @Test
    void findBySessionIdTest_notFound() {
        // when
        List<CoverImage> result = coverImageRepository.findByAllSessionId(1L);

        // then
        assertThat(result).isEmpty();
    }

}
