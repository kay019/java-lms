package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sessions {
  private List<Session> sessions;

  public Sessions() {
    this(Collections.emptyList());
  }

  public Sessions(List<Session> sessions) {
    this.sessions = new ArrayList<>(sessions);
  }

  public void add(Session session) {
    this.sessions.add(session);
  }

  public int size() {
    return sessions.size();
  }
}
