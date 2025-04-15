package nextstep.session.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.Course;
import nextstep.session.domain.constraint.SessionConstraint;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Session extends BaseDomain {

    private Course course;

    private final SessionConstraint constraint;

    private final SessionDescriptor descriptor;

    public Session(Long id, Course course, SessionConstraint constraint, SessionDescriptor descriptor) {
        super(id);
        this.course = course;
        this.descriptor = descriptor;
        this.constraint = constraint;
    }

    public BufferedImage image() {
        return descriptor.image();
    }

    public boolean canEnroll(int enrollCount, long amount) {
        return descriptor.canEnroll(constraint, enrollCount, amount);
    }

    public void updateImage() throws IOException {
        descriptor.updateImage();
    }
}
