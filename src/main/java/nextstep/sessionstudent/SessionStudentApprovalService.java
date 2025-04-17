package nextstep.sessionstudent;

import org.springframework.stereotype.Service;

import nextstep.users.domain.NsUser;

@Service
public class SessionStudentApprovalService {

    private final SessionStudentApprovalPolicy sessionStudentApprovalPolicy;
    private final SessionStudentService sessionStudentService;

    public SessionStudentApprovalService(
        SessionStudentApprovalPolicy sessionStudentApprovalPolicy,
        SessionStudentService sessionStudentService) {
        this.sessionStudentApprovalPolicy = sessionStudentApprovalPolicy;
        this.sessionStudentService = sessionStudentService;
    }

    public SessionStudent approve(long sessionStudentId, NsUser nsUser) {
        SessionStudent sessionStudent = sessionStudentService.findById(sessionStudentId);
        sessionStudentApprovalPolicy.validate(sessionStudent, nsUser);
        sessionStudent.approve();
        return sessionStudent;
    }

    public SessionStudent cancel(long sessionStudentId, NsUser nsUser) {
        SessionStudent sessionStudent = sessionStudentService.findById(sessionStudentId);
        sessionStudentApprovalPolicy.validate(sessionStudent, nsUser);
        sessionStudent.cancel();
        return sessionStudent;
    }

}
