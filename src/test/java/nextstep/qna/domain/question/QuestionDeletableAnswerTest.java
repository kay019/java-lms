package nextstep.qna.domain.question;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.AnswerTest;
import nextstep.qna.domain.Question;
import nextstep.qna.domain.qustion.QuestionDeletable;
import nextstep.qna.domain.qustion.QuestionDeletableAnswer;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

class QuestionDeletableAnswerTest {

    @Test
    void 답변없음_삭제가능() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        QuestionDeletable questionDeletable = new QuestionDeletableAnswer();

        assertThatNoException().isThrownBy(() -> questionDeletable.checkDeletable(question, question.getWriter()));
    }

    @Test
    void 작성자와_같은답변_삭제가능() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        QuestionDeletable questionDeletable = new QuestionDeletableAnswer();
        question.addAnswer(AnswerTest.A1);

        assertThatNoException().isThrownBy(() -> questionDeletable.checkDeletable(question, question.getWriter()));
    }

    @Test
    void 작성자와_다른답변_삭제불가() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        QuestionDeletable questionDeletable = new QuestionDeletableAnswer();
        question.addAnswer(AnswerTest.A2);

        assertThatThrownBy(() -> questionDeletable.checkDeletable(question, question.getWriter())).isInstanceOf(
                CannotDeleteException.class);
    }
}
