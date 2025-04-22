package nextstep.session.image.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.session.image.domain.ImageFileSize;
import nextstep.session.image.domain.ImageSize;
import nextstep.session.image.domain.ImageType;
import nextstep.session.image.domain.SessionCoverImage;
import nextstep.session.image.repository.SessionCoverImageRepository;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionCoverImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionCoverImageRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionCoverImageRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JdbcSessionCoverImageRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("SessionCoverImage 저장 후 조회 시 동일한 값 반환")
    void saveAndFindById() {
        SessionCoverImage image = new SessionCoverImage(
            0L,
            100L,
            new ImageFileSize(1024),
            ImageType.JPG,
            new ImageSize(300, 200)
        );

        int saveResult = repository.save(image);
        SessionCoverImage found = repository.findById(1L);

        assertThat(saveResult).isEqualTo(1);

        SessionCoverImage expected = new SessionCoverImage(
            1L,
            image.getSessionId(),
            image.getFileSize(),
            image.getType(),
            image.getImageSize()
        );

        assertThat(found)
            .usingRecursiveComparison()
            .isEqualTo(expected);

        LOGGER.debug("SessionCoverImage: {}", found);
    }
}
