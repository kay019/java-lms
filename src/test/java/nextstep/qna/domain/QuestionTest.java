package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;

import nextstep.qna.CannotDeleteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.users.domain.NsUserTest;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    @DisplayName("내가 쓴 질문은 삭제할 수 있다.")
    void testDelete() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

        assertThat(question.isDeleted()).isFalse();
        question.delete(NsUserTest.JAVAJIGI);
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("다른 사람이 쓴 질문은 삭제할 수 없다.")
    void testDeleteWithSameWriter() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

        assertThatThrownBy(() -> {
            question.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("질문자와 답변 글의 모든 답변자가 같은 경우 삭제가 가능하다.")
    void testDeleteWithSameWriterAndAnswer() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        question.addAnswer(new Answer(NsUserTest.JAVAJIGI, question, "answer1"));

        assertThat(question.isDeleted()).isFalse();
        question.delete(NsUserTest.JAVAJIGI);
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("질문자와 답변 글의 답변자가 다른 경우 삭제가 불가능하다.")
    void testDeleteWithDifferentWriter() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        question.addAnswer(new Answer(NsUserTest.SANJIGI, question, "answer1"));

        assertThatThrownBy(() -> {
            question.delete(NsUserTest.JAVAJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("질문을 삭제할 때 답변 또한 삭제한다.")
    void testDeleteWithAnswer() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        question.addAnswer(new Answer(NsUserTest.JAVAJIGI, question, "answer1"));

        assertThat(question.isDeleted()).isFalse();
        question.delete(NsUserTest.JAVAJIGI);
        assertThat(question.isDeleted()).isTrue();
        assertThat(question.isAllAnswersDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 이력을 생성할 수 있다.")
    void testCreateDeleteHistories() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        question.delete(NsUserTest.JAVAJIGI);

        DeleteHistories deleteHistories = question.getDeleteHistories();
        
        assertThat(deleteHistories.size()).isEqualTo(1);
        
        List<DeleteHistory> histories = deleteHistories.getHistories();
        DeleteHistory expected = new DeleteHistory(ContentType.QUESTION, question.getId(), NsUserTest.JAVAJIGI, LocalDateTime.now());
        
        assertThat(histories).hasSize(1);
        assertThat(histories.get(0)).isEqualTo(expected);
    }

    @Test
    @DisplayName("답변이 있는 경우 모든 답변의 삭제 이력도 생성할 수 있다.")
    void testCreateDeleteHistoriesWithAnswers() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer = new Answer(NsUserTest.JAVAJIGI, question, "answer1");
        question.addAnswer(answer);
        question.delete(NsUserTest.JAVAJIGI);

        DeleteHistories deleteHistories = question.getDeleteHistories();
        
        assertThat(deleteHistories.size()).isEqualTo(2);
        
        List<DeleteHistory> histories = deleteHistories.getHistories();
        
        DeleteHistory expectedQuestionHistory = new DeleteHistory(ContentType.QUESTION, question.getId(), NsUserTest.JAVAJIGI, LocalDateTime.now());
        DeleteHistory expectedAnswerHistory = new DeleteHistory(ContentType.ANSWER, answer.getId(), NsUserTest.JAVAJIGI, LocalDateTime.now());
        
        assertThat(histories).hasSize(2);
        assertThat(histories).contains(expectedQuestionHistory, expectedAnswerHistory);
    }
}
