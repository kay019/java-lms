package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;

import static nextstep.qna.domain.DeleteHistory.from;

public class DeleteHistories {
    private List<DeleteHistory> deleteHistories;

    public DeleteHistories() {
        this.deleteHistories = new ArrayList<>();
    }

    public List<DeleteHistory> getDeleteHistories() {
        return deleteHistories;
    }

    public List<DeleteHistory> makeDeleteHistories(Question question) {
        deleteHistories.add(from(question));
        for (Answer answer : question.getAnswers()) {
            answer.delete();
            deleteHistories.add(from(answer));
        }
        return deleteHistories;
    }
}
