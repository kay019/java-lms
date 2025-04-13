package nextstep.sessions.domain;

import java.util.Objects;

public enum SessionStatus {
    READY("ready"),
    OPEN("open"),
    CLOSED("closed");

    private final String label;

    SessionStatus(String label) {
        this.label = label;
    }

    public boolean isOpened() {
        return label.equals("open");
    }
}
