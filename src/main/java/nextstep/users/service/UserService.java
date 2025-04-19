package nextstep.users.service;

import nextstep.courses.domain.CourseSession;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public NsUser findById(final String id) {
        return userRepository.findByUserId(id).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
    }
}
