package nextstep.courses.infrastructure;

import nextstep.courses.domain.Image;
import nextstep.courses.entity.ImageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ImageRepositoryTest {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private JdbcImageRepository imageRepository;

  @BeforeEach
  void setUp() {
    imageRepository = new JdbcImageRepository(jdbcTemplate);
  }

  @Test
  void crud() {
    Image image = new Image("test.jpg");
    long id = imageRepository.save(image.toImageEntity());

    ImageEntity savedImage = imageRepository.findById(id);
    assertThat(savedImage.filename()).isEqualTo("test.jpg");
  }
}
