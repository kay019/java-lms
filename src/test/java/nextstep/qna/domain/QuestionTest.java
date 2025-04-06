package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionTest {

    private NsUser user;
    private NsUser otherUser;
    private long questionId;
    private Question question;

    @BeforeEach
    void setUp() {
        questionId = 1L;
        user = new NsUser(1L, "user1", "password", "user1", "user1_email");
        otherUser = new NsUser(2L, "user2", "password", "user2", "user2_email");
        question = new Question(questionId, user, "title", "contents");
    }

    @Test
    void delete_답변_없는_질문_삭제() throws CannotDeleteException {
        List<DeleteHistory> deleteHistories = question.delete(user);

        assertTrue(question.isDeleted());
        assertEquals(1, deleteHistories.size());
        assertEquals(new DeleteHistory(ContentType.QUESTION, questionId, user), deleteHistories.get(0));
    }

    @Test
    void delete_답변_없는_질문_다른_사용자가_삭제_시도() {
        assertThatThrownBy(() -> question.delete(otherUser))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    void delete_답변이_있는_질문_같은_작성자_삭제() throws CannotDeleteException {
        question.addAnswer(new Answer(1L, user, question, "answer1"));
        List<DeleteHistory> deleteHistories = question.delete(user);

        assertTrue(question.isDeleted());
        assertEquals(2, deleteHistories.size());
        assertEquals(new DeleteHistory(ContentType.QUESTION, questionId, user), deleteHistories.get(0));
        assertEquals(new DeleteHistory(ContentType.ANSWER, 1L, user), deleteHistories.get(1));
    }

    @Test
    void delete_답변이_있는_질문_다른_작성자_삭제() {
        question.addAnswer(new Answer(1L, user, question, "answer1"));
        question.addAnswer(new Answer(2L, otherUser, question, "answer2"));
        assertThatThrownBy(() -> question.delete(user))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
