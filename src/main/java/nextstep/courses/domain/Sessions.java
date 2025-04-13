package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sessions {
  private final List<Session> sessions;

  public Sessions() {
    this(Collections.emptyList());
  }

  public Sessions(Sessions existingSessions, Session newSession) {
    this(createNewSessions(existingSessions.sessions, newSession));
  }

  public Sessions(List<Session> sessions) {
    this.sessions = Collections.unmodifiableList(sessions);
  }

  private static List<Session> createNewSessions(List<Session> sessions, Session newSession) {
    List<Session> newList = new ArrayList<>(sessions);
    newList.add(newSession);
    return newList;
  }

  public void add(Session session) {
    this.sessions.add(session);
  }

  public int size() {
    return sessions.size();
  }
}
