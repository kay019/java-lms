package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;

public class DeleteHistories {
    private final List<DeleteHistory> values;

    public DeleteHistories() {
        this(new ArrayList<>());
    }

    public DeleteHistories(List<DeleteHistory> values) {
        this.values = values;
    }

    public int size() {
        return this.values.size();
    }

    public DeleteHistories concat(DeleteHistories other) {
        this.values.addAll(other.values);

        return this;
    }

    public DeleteHistories add(DeleteHistory deleteHistory) {
        this.values.add(deleteHistory);

        return this;
    }

    public List<DeleteHistory> toList() {
        return this.values;
    }
}
