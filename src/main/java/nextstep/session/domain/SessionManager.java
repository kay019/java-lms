package nextstep.session.domain;

import nextstep.session.exception.NoAuthorityException;

public class SessionManager {
    private final Session session;
    private final ManagerAuthority authority;

    public SessionManager(Session session) {
        this(session, ManagerAuthority.MANAGER);
    }

    SessionManager(Session session, ManagerAuthority authority) {
        this.session = session;
        this.authority = authority;
    }

    public void selectStudent(long studentId) {
        validate();
        session.selectStudent(studentId);
    }

    public void approveStudent(long studentId) {
        validate();
        session.approveStudent(studentId);
    }

    public void cancelStudent(long studentId) {
        validate();
        session.cancelStudent(studentId);
    }

    private void validate() {
        if(!authority.isManager()) {
            throw new NoAuthorityException();
        }
    }
}
