package nextstep.qna.domain;

import java.util.Objects;
import java.util.Set;

public class DeleteHistories {
    private final Set<DeleteHistory> deleteHistories;

    public DeleteHistories(Set<DeleteHistory> deleteHistories) {
        this.deleteHistories = deleteHistories;
    }

    public void add(DeleteHistory deleteHistory) {
        if (deleteHistory != null) {
            deleteHistories.add(deleteHistory);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DeleteHistories that = (DeleteHistories) o;
        return Objects.equals(deleteHistories, that.deleteHistories);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(deleteHistories);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DeleteHistory deleteHistory : deleteHistories) {
            sb.append(deleteHistory);
        }
        return sb.toString();
    }
}
