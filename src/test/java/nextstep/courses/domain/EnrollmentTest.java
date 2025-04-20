package nextstep.courses.domain;

import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.courses.enrollment.domain.Enrollments;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentTest {
    public static final Enrollment ENROLLMENT1 = new Enrollment(SessionTestFixtures.freeSession(), NsUserTest.JAVAJIGI, new Payment());
    public static final Enrollment ENROLLMENT2 = new Enrollment(SessionTestFixtures.freeSession(), NsUserTest.SANJIGI, new Payment());
    public static final Enrollments ENROLLMENTS = new Enrollments(new ArrayList<>(List.of(ENROLLMENT1, ENROLLMENT2)));
}
