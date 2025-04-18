package nextstep.courses.domain.session;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.image.SessionImageType;
import nextstep.courses.domain.session.image.URLImageHandler;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;
import nextstep.courses.domain.session.policy.SessionStatus;
import nextstep.courses.domain.session.policy.SessionType;
import nextstep.courses.entity.SessionEntity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session extends BaseDomain {

    private final SessionConstraint constraint;

    private final SessionDescriptor descriptor;

    public static Session from(SessionEntity sessionEntity) throws IOException {
        SessionConstraint sessionConstraint = new SessionConstraint(sessionEntity.getFee(), sessionEntity.getCapacity());
        SessionDescriptor sessionDescriptor = new SessionDescriptor(
            new SessionImage(sessionEntity.getImageUrl(),  new URLImageHandler(sessionEntity.getImageUrl()), SessionImageType.fromString(sessionEntity.getImageType())),
            new SessionPeriod(sessionEntity.getStartDate(), sessionEntity.getEndDate()),
            new SessionEnrollPolicy(SessionStatus.fromString(sessionEntity.getStatus()), SessionType.fromString(sessionEntity.getType()))
        );
        return new Session(sessionEntity.getId(), sessionConstraint, sessionDescriptor);
    }

    public static List<Session> from(List<SessionEntity> sessionEntities) throws IOException {
        List<Session> resultList = new ArrayList<>();
        for (SessionEntity sessionEntity : sessionEntities) {
            resultList.add(from(sessionEntity));
        }
        return resultList;
    }

    public Session() {
        this(null, null);
    }

    public Session(Long id) {
        this(id, null, null);
    }

    public Session(SessionConstraint constraint, SessionDescriptor descriptor) {
        this(null, constraint, descriptor);
    }

    public Session(Long id, SessionConstraint constraint, SessionDescriptor descriptor) {
        super(id);
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

    public SessionEntity to(Long courseId) {
        return SessionEntity.builder()
            .id(id)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .deleted(deleted)
            .courseId(courseId)
            .fee(constraint.fee())
            .capacity(constraint.capacity())
            .imageUrl(descriptor.imageUrl())
            .imageType(descriptor.imageType())
            .startDate(descriptor.startDate())
            .endDate(descriptor.endDate())
            .type(descriptor.type())
            .status(descriptor.status())
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Session session = (Session) o;
        return Objects.equals(constraint, session.constraint) &&
            Objects.equals(descriptor, session.descriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), constraint, descriptor);
    }
}
