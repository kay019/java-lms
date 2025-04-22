package nextstep.courses.service;

import nextstep.courses.domain.Enrollment;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionFactory;
import nextstep.courses.entity.CohortEntity;
import nextstep.courses.entity.CourseEntity;
import nextstep.courses.entity.EnrollmentEntity;
import nextstep.courses.entity.ImageEntity;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.repository.CohortRepository;
import nextstep.courses.repository.CourseRepository;
import nextstep.courses.repository.EnrollmentRepository;
import nextstep.courses.repository.ImageRepository;
import nextstep.courses.repository.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("sessionService")
@Transactional
public class SessionService {

  private final SessionRepository sessionRepository;
  private final CourseRepository courseRepository;
  private final CohortRepository cohortRepository;
  private final ImageRepository imageRepository;
  private final EnrollmentRepository enrollmentRepository;
  private final UserRepository userRepository;

  public SessionService(SessionRepository sessionRepository,
                        CourseRepository courseRepository,
                        CohortRepository cohortRepository,
                        ImageRepository imageRepository,
                        EnrollmentRepository enrollmentRepository,
                        UserRepository userRepository) {
    this.sessionRepository = sessionRepository;
    this.courseRepository = courseRepository;
    this.cohortRepository = cohortRepository;
    this.imageRepository = imageRepository;
    this.enrollmentRepository = enrollmentRepository;
    this.userRepository = userRepository;
  }

  public long save(Session session) {
    long sessionId = sessionRepository.save(session.toSessionEntity());
    Enrollments enrollments = session.enrollments();
    List<EnrollmentEntity> enrollmentEntities = enrollments.getEnrollments().stream()
            .map(enrollment -> enrollment.toEnrollmentEntity(sessionId))
            .collect(Collectors.toList());
    enrollmentRepository.saveAll(enrollmentEntities);

    return sessionId;
  }

  public Session findById(Long id){
    SessionEntity sessionEntity = sessionRepository.findById(id);
    CourseEntity courseEntity = courseRepository.findById(sessionEntity.courseId());
    CohortEntity cohortEntity = cohortRepository.findByCourseId(courseEntity.id());
    ImageEntity imageEntity = imageRepository.findById(sessionEntity.coverImageId());
    List<EnrollmentEntity> enrollmentEntities = enrollmentRepository.findBySessionId(id);
    Enrollments enrollments = new Enrollments(enrollmentEntities.stream()
            .map(enrollmentEntity -> EnrollmentEntity.toEnrollment(userRepository.findByUserId(enrollmentEntity.studentId()), sessionEntity.id()))
            .collect(Collectors.toList())
    );

    return SessionFactory.createSessionFromSessionEntity(
            sessionEntity,
            courseEntity.toCourse(cohortEntity.toCohort()),
            imageEntity.toImage(),
            enrollments
    );
  }

  public void enroll(Session session, NsUser student, Payment payment) {
    Enrollment enrollment = session.enroll(student, payment);
    enrollmentRepository.save(enrollment.toEnrollmentEntity(session.id()));
  }

  public Long saveImage(Image image) {
    return imageRepository.save(image.toImageEntity());
  }

  public Image findImageById(Long id) {
    return imageRepository.findById(id).toImage();
  }
}
