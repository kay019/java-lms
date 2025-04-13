package nextstep.courses.domain;

public class Cohort {
  private Long id;
  private String name;

  public Cohort() {
    this(0L, null);
  }

  public Cohort(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
