package nextstep.courses.infrastructure;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageRepository;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ImageRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        imageRepository = new JdbcImageRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("이미지 저장")
    void save() {
        Image image = new Image("https://example.com/image.jpg", 300, 200, "jpg", 1000);
        Image savedImage = imageRepository.save(image);
        assertThat(savedImage).usingRecursiveComparison().ignoringFields("id").isEqualTo(image);
        assertThat(savedImage.getId()).isNotNull();
    }

    @Test
    @DisplayName("이미지 조회")
    void findById() {
        Image image = new Image("https://example.com/image.jpg", 300, 200, "jpg", 1000);
        Image savedImage = imageRepository.save(image);
        Optional<Image> foundImage = imageRepository.findById(savedImage.getId());
        assertThat(foundImage).isEqualTo(Optional.of(savedImage));
    }

}