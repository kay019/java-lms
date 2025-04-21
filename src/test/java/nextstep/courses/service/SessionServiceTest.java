package nextstep.courses.service;

import nextstep.courses.domain.*;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    private Session session;
    private NsUser user1;

    @BeforeEach
    public void setUp() {
        session = new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 15, 15, 30)
                , SessionState.RECRUTING
                , new FreePayStrategy()
                , new Image(1000L, ImageType.GIF, 300L, 200L)
                , 10L);
        user1 = new NsUser(1L, "user1", "12345678", "pikmin", "pikmin@gmail.com");
    }

    @Test
    public void register() {
        when(sessionRepository.findById(session.getId())).thenReturn(session);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        assertThat(session.getRegistry().getStudents()).hasSize(0);
        sessionService.registerSession(0L, 1L, 1000L);

        assertThat(session.getRegistry().getStudents()).hasSize(1);
    }
}
