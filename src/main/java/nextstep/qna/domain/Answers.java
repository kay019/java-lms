package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Answers {
    private final List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public boolean hasAnswerFromOtherUser(NsUser user) {
        for (Answer answer : answers) {
            if (!answer.isOwner(user)) {
                return true;
            }
        }
        return false;
    }

    public void delete() {
        for (Answer answer : answers) {
            answer.setDeleted(true);
        }
    }

    public List<DeleteHistory> collectDeleteHistory() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        for (Answer answer : answers) {
            deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now()));
        }
        return deleteHistories;
    }
}

