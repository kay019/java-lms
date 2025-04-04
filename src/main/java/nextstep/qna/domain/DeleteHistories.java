package nextstep.qna.domain;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DeleteHistories {
    private final List<DeleteHistory> deleteHistories;

    public DeleteHistories(List<DeleteHistory> deleteHistories) {
        this.deleteHistories = deleteHistories;
    }

    public List<DeleteHistory> getDeleteHistories(Predicate<DeleteHistory> predicate) {
        return deleteHistories
            .stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

    public List<DeleteHistory> getAllDeleteHistories() {
        return getDeleteHistories(h -> true);
    }

    public DeleteHistory getQuestionHistory() {
        return getDeleteHistories(h -> h.getContentType() == ContentType.QUESTION)
            .stream()
            .findFirst()
            .orElseThrow();
    }

    public List<DeleteHistory> getAnswerHistories() {
        return getDeleteHistories(h -> h.getContentType() == ContentType.ANSWER);
    }
}
