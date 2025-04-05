package nextstep.qna.domain;

import java.time.LocalDateTime;

import nextstep.users.domain.NsUser;

public class DeleteHistoryFactory {

    public static DeleteHistory ofQuestion(Long contentId, NsUser deletedBy, LocalDateTime createdDate) {
        return new DeleteHistory(ContentType.QUESTION, contentId, deletedBy, createdDate);
    }

    public static DeleteHistory ofAnswer(Long contentId, NsUser deletedBy, LocalDateTime createdDate) {
        return new DeleteHistory(ContentType.ANSWER, contentId, deletedBy, createdDate);
    }

}
