package nextstep.courses.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.courses.domain.Participant;
import nextstep.courses.domain.ParticipantRepository;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ParticipantRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ParticipantRepository participantRepository;

    @BeforeEach
    void setUp() {
        participantRepository = new JdbcParticipantRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("참가자를 저장할 수 있다.")
    void save() {
        Participant participant = new Participant(1L);
        int count = participantRepository.save(1L, participant);

        assertThat(count).isEqualTo(1);
    }
}
