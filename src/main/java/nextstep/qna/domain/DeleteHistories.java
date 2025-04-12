package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DeleteHistories {
    private final List<DeleteHistory> histories;

    public DeleteHistories() {
        this(new ArrayList<>());
    }

    public DeleteHistories(List<DeleteHistory> histories) {
        Objects.requireNonNull(histories, "삭제 이력 목록은 null이 될 수 없습니다.");
        this.histories = new ArrayList<>(histories);
    }

    public void add(DeleteHistory history) {
        Objects.requireNonNull(history, "삭제 이력은 null이 될 수 없습니다.");
        histories.add(history);
    }

    public void addAll(DeleteHistories otherHistories) {
        Objects.requireNonNull(otherHistories, "삭제 이력 목록은 null이 될 수 없습니다.");
        histories.addAll(otherHistories.histories);
    }

    public int size() {
        return histories.size();
    }

    public List<DeleteHistory> getHistories() {
        return Collections.unmodifiableList(histories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteHistories that = (DeleteHistories) o;
        return Objects.equals(histories, that.histories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(histories);
    }

    @Override
    public String toString() {
        return "DeleteHistories{" +
                "count=" + histories.size() +
                ", histories=" + histories +
                '}';
    }
} 