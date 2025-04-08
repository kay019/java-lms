package nextstep.courses.service;

import nextstep.courses.record.CoverImageRecord;
import nextstep.courses.record.SessionRecord;
import nextstep.courses.domain.*;
import nextstep.payments.record.PaymentRecord;
import nextstep.payments.domain.PaymentRepository;
import nextstep.payments.record.PaymentRecords;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUsers;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
    private final CoverImageRepository coverimageRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository,
                          CourseRepository courseRepository,
                          CoverImageRepository coverimageRepository,
                          PaymentRepository paymentRepository,
                          UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.courseRepository = courseRepository;
        this.coverimageRepository = coverimageRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    public Session findById(long id) {
        SessionRecord sessionRecord = sessionRepository.findById(id);
        Course course = courseRepository.findById(sessionRecord.getCourseId());
        CoverImageRecord coverImageRecord = coverimageRepository.findById(sessionRecord.getCoverImageId());
        CoverImage coverImage = coverImageRecord.toCoverImage();

        PaymentRecords paymentRecords
            = new PaymentRecords(paymentRepository.findBySessionId(sessionRecord.getId()));
        List<Long> nsUserIds = paymentRecords.getNsUserIds();
        NsUsers nsUsers = new NsUsers(userRepository.findByIds(nsUserIds));
        paymentRecords.withNsUsers(nsUsers);

        return sessionRecord.toSession(course, coverImage, paymentRecords);
    }
}
