package nextstep.courses.service;

import javax.annotation.Resource;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.users.domain.NsUser;

public class SessionService {

  @Resource(name = "sessionRepository")
  private SessionRepository sessionRepository;

  @Resource(name = "paymentRepository")
  private PaymentRepository paymentRepository;

  public void enroll(NsUser loginUser, Long sessionId, long amount) {
    Session session = sessionRepository.findById(sessionId);
    paymentRepository.save(new Payment(sessionId, loginUser.getId(), amount));
    session.enroll(loginUser, amount);



  }
}
