package nextstep.courses.domain;

import nextstep.courses.entity.CohortEntity;
import nextstep.courses.utils.IdGenerator;

import java.util.Objects;

public class Cohort {
  private final Long id;
  private final String name;

  public Cohort() {
    this(IdGenerator.generate(), null);
  }

  public Cohort(String name) {
    this(IdGenerator.generate(), name);
  }

  public Cohort(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long id() {
    return id;
  }

  public CohortEntity toCohortEntity(Long courseId) {
    return new CohortEntity(id, courseId, name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cohort cohort = (Cohort) o;
    return Objects.equals(id, cohort.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
