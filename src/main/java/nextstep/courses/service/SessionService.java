package nextstep.courses.service;

import nextstep.courses.CannotRegisterException;
import nextstep.courses.PayStrategyFactory;
import nextstep.courses.domain.*;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("sessionService")
public class SessionService {
    @Resource(name = "sessionRepository")
    private final SessionRepository sessionRepository;

    @Resource(name = "userRepository")
    private final UserRepository userRepository;

    private final PayStrategyFactory payStrategyFactory;

    public SessionService(SessionRepository sessionRepository,
                          UserRepository userRepository,
                          PayStrategyFactory payStrategyFactory) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.payStrategyFactory = payStrategyFactory;
    }

    public void registerSession(Long sessionId, Long userId, Long price) {
        Session targetSession = sessionRepository.findById(sessionId);
        NsUser targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new CannotRegisterException("해당하는 유저가 없습니다."));
        targetSession.register(targetUser, new PositiveNumber(price));
        sessionRepository.save(targetSession);
    }
}
