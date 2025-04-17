package nextstep.sessionstudent;

import nextstep.courses.domain.Course;
import nextstep.session.domain.Session;
import nextstep.users.domain.NsUser;

public class ApprovalContext {

    private final boolean sessionSelectionRequired;
    private final boolean courseOwner;

    public ApprovalContext(Course course, Session session, NsUser loginUser) {
        this.sessionSelectionRequired = session.isSelectionRequired();
        this.courseOwner = course.isOwner(loginUser);
    }

    ApprovalContext(boolean sessionSelectionRequired, boolean courseOwner) {
        this.sessionSelectionRequired = sessionSelectionRequired;
        this.courseOwner = courseOwner;
    }

    public boolean isSessionSelectionRequired() {
        return sessionSelectionRequired;
    }

    public boolean isCourseOwner() {
        return courseOwner;
    }

}
