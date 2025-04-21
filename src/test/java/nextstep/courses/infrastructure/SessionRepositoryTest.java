package nextstep.courses.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionImageInfo;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class SessionRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private SessionRepository sessionRepository;

  @BeforeEach
  void setUp() {
    sessionRepository = new JdbcSessionRepository(jdbcTemplate);
  }

  @Test
  void save_and_find_session() {
    Session session = new Session(
        LocalDateTime.of(2025, 4, 20, 10, 0),
        LocalDateTime.of(2025, 4, 20, 12, 0),
        new SessionImageInfo(ImageType.PNG, 1024, 900, 600),
        false, 10000, 30, SessionStatus.RECRUITING
    );
    int result = sessionRepository.save(session);
    assertThat(result).isEqualTo(1);

    Session savedSession = sessionRepository.findById(1L);
    assertThat(savedSession.getStartDate()).isEqualTo(session.getStartDate());
    assertThat(savedSession.getStatus()).isEqualTo(SessionStatus.RECRUITING);
  }
}
