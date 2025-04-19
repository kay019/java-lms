package nextstep.courses.domain;

import nextstep.courses.entity.EnrollmentEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Enrollments {
  private List<Enrollment> enrollments;

  public Enrollments() {
    this(Collections.emptyList());
  }

  public Enrollments(List<Enrollment> enrollments) {
    validate(enrollments);
    this.enrollments = new ArrayList<>(enrollments);
  }

  private void validate(List<Enrollment> enrollments) {
    Set<Enrollment> enrollmentSet = new HashSet<>(enrollments);
    if (enrollmentSet.size() != enrollments.size()) {
      throw new IllegalArgumentException("이미 수강신청한 사용자입니다.");
    }
  }

  public void add(Enrollment enrollment) {
    if (enrollments.contains(enrollment)) {
      throw new IllegalArgumentException("이미 수강신청한 사용자입니다.");
    }
    this.enrollments.add(enrollment);
  }

  public int size() {
    return enrollments.size();
  }

  public List<Enrollment> getEnrollments() {
    return Collections.unmodifiableList(enrollments);
  }

  public List<EnrollmentEntity> toEnrollmentEntities() {
    List<EnrollmentEntity> enrollmentEntities = new ArrayList<>();
    for (Enrollment enrollment : enrollments) {
      enrollmentEntities.add(enrollment.toEnrollmentEntity());
    }
    return enrollmentEntities;
  }
}
