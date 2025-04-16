package nextstep.courses.domain.session.policy;

import nextstep.courses.domain.session.constraint.SessionConstraint;

public interface EnrollStrategy {
    boolean canEnroll(SessionConstraint sessionConstraint, int enrollmentCount, long amount);
}
