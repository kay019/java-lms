package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");


    @Test
    @DisplayName("deleteWithAnswers - 직접 작성한 질문이 아니면 삭제할 수 없다.")
    void validateDeletionTest1() {
        assertThatThrownBy(() ->
            Q1.deleteWithAnswers(NsUserTest.STRANGER)
        ).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("deleteWithAnswers - 다른 사람의 댓글이 존재하면 삭제할 수 없다.")
    void validateDeletionTest2() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title", "contents");
        question.addAnswer(new Answer(NsUserTest.STRANGER, question, "contents"));
        assertThatThrownBy(() ->
            question.deleteWithAnswers(question.getWriter())
        ).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("deleteWithAnswers - Question & Answer의 DeletedHistories를 가져온다.")
    void deletedHistoriesTest2() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title", "contents");
        Answer answer = new Answer(NsUserTest.JAVAJIGI, question, "contents");
        question.addAnswer(answer);

        Set<DeleteHistory> histories = new HashSet<>(question.deleteWithAnswers(NsUserTest.JAVAJIGI));
        Set<DeleteHistory> expectedHistories = Set.of(
                new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now()),
                new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter(), LocalDateTime.now())
        );
        assertThat(histories.equals(expectedHistories)).isTrue();
    }

}
