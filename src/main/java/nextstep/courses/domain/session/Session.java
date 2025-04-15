package nextstep.courses.domain.session;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.Course;
import nextstep.courses.domain.session.constraint.SessionConstraint;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

public class Session extends BaseDomain {

    private Course course;

    private final SessionConstraint constraint;

    private final SessionDescriptor descriptor;


    public Session(Course course, SessionConstraint constraint, SessionDescriptor descriptor) {
        this(null, course, constraint, descriptor);
    }

    public Session(Long id, Course course, SessionConstraint constraint, SessionDescriptor descriptor) {
        super(id);
        this.course = course;
        this.descriptor = descriptor;
        this.constraint = constraint;
    }

    public BufferedImage image() {
        return descriptor.image();
    }

    public void delete() {
        this.deleted = true;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean canEnroll(int enrollCount, long amount) {
        return descriptor.canEnroll(constraint, enrollCount, amount);
    }

    public void updateImage() throws IOException {
        descriptor.updateImage();
    }
}
