package nextstep.courses.entity;

import nextstep.courses.domain.Cohort;

public class CohortEntity {
  private Long id;
  private Long courseId;
  private String name;

  public CohortEntity(Long id, Long courseId, String name) {
    this.id = id;
    this.courseId = courseId;
    this.name = name;
  }

  public Long id() {
    return id;
  }
  public Long courseId() {
    return courseId;
  }
  public String name() {
    return name;
  }

  public Cohort toCohort() {
    return new Cohort(id, name);
  }
}
