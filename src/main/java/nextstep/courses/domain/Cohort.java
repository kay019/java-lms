package nextstep.courses.domain;

public class Cohort {
  private final Long id;
  private final String name;

  public Cohort() {
    this(0L, null);
  }

  public Cohort(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
