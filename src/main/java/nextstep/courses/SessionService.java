package nextstep.courses;

import nextstep.courses.domain.Image;
import nextstep.courses.domain.Registry;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("sessionService")
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final PayStrategyFactory payStrategyFactory;

    public SessionService(SessionRepository sessionRepository,
                          UserRepository userRepository,
                          PayStrategyFactory payStrategyFactory) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.payStrategyFactory = payStrategyFactory;
    }

    public Session loadSession(Long sessionId) {
        SessionDto SessionDto = sessionRepository.findSessionDtoById(sessionId);
        RegistryDto registryDto = sessionRepository.findRegistryDtoBySessionId(sessionId);
        List<Long> userIds = sessionRepository.findStudentIdBySessionId(sessionId);

        List<NsStudent> students = userIds.stream()
                .map(id -> new NsStudent(
                        userRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. id=" + id)),
                        sessionId
                )).collect(Collectors.toList());

        Registry registry = new Registry(
                students,
                payStrategyFactory.getStrategy(registryDto.getPayStrategy()),
                registryDto.getSessionState(),
                registryDto.getCapacity()
        );

        Image image = new Image(
                SessionDto.getImageSize(),
                SessionDto.getImageType(),
                SessionDto.getImageWidth(),
                SessionDto.getImageHeight()
        );

        return new Session(
                SessionDto.getId(),
                SessionDto.getStartAt(),
                SessionDto.getEndAt(),
                registry,
                image,
                SessionDto.getPrice()
        );
    }
}
