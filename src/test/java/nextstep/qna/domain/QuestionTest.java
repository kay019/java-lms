package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionTest {

    private NsUser user;
    private NsUser otherUser;
    private Question question;

    @BeforeEach
    void setUp() {
        user = new NsUser(1L, "user1", "password", "user1", "user1_email");
        otherUser = new NsUser(2L, "user2", "password", "user2", "user2_email");
        question = new Question(1L, user, "title", "contents");
        question.addAnswer(new Answer(1L, user, question, "answer1"));
    }

    @Test
    void delete_성공() throws CannotDeleteException {
        List<DeleteHistory> deleteHistories = question.delete(user);

        assertTrue(question.isDeleted());
        assertEquals(2, deleteHistories.size());
    }

    @Test
    void delete_다른_사용자가_삭제_시도() {
        question.addAnswer(new Answer(2L, otherUser, question, "answer2"));
        assertThrows(CannotDeleteException.class, () -> question.delete(otherUser));
    }
}
