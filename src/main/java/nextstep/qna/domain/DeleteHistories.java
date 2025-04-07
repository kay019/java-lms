package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeleteHistories {
    private final List<DeleteHistory> deleteHistories;

    public DeleteHistories(List<DeleteHistory> deleteHistories) {
        this.deleteHistories = deleteHistories;
    }

    public static DeleteHistories from(Question question) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(new DeleteHistory(question));
        question.answers()
                .forEach(answer -> deleteHistories.add(new DeleteHistory(answer)));
        return new DeleteHistories(deleteHistories);
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
}
