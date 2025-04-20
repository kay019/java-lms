package nextstep.courses.service;

import lombok.RequiredArgsConstructor;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public void enrollUser(Long sessionId, Payment payment) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));
        session.enroll(payment);
    }
}