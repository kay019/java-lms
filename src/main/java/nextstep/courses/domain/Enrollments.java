package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Enrollments {
  private final List<Enrollment> enrollments;

  public Enrollments() {
    this(Collections.emptyList());
  }

  public Enrollments(Enrollments existingEnrollments, Enrollment newEnrollment) {
    this(createNewEnrollments(existingEnrollments.enrollments, newEnrollment));
  }

  public Enrollments(List<Enrollment> enrollments) {
    validate(enrollments);
    this.enrollments = Collections.unmodifiableList(enrollments);
  }

  private void validate(List<Enrollment> enrollments) {
    Set<Enrollment> enrollmentSet = new HashSet<>(enrollments);
    if (enrollmentSet.size() != enrollments.size()) {
      throw new IllegalArgumentException("이미 수강신청한 사용자입니다.");
    }
  }

  private static List<Enrollment> createNewEnrollments(List<Enrollment> enrollments, Enrollment newEnrollment) {
    List<Enrollment> newList = new ArrayList<>(enrollments);
    newList.add(newEnrollment);
    return newList;
  }

  public int size() {
    return enrollments.size();
  }
}
