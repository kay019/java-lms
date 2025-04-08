package nextstep.qna.domain.question;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.Question;
import nextstep.qna.domain.qustion.QuestionDeletable;
import nextstep.qna.domain.qustion.QuestionDeletableWriter;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuestionDeletableWriterTest {

    @Test
    void 작성자라_삭제가능() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        QuestionDeletable questionDeletable = new QuestionDeletableWriter();

        assertThatNoException().isThrownBy(() -> questionDeletable.checkDeletable(question, NsUserTest.JAVAJIGI));
    }

    @Test
    void 작성자가_아니라_삭제불가() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        QuestionDeletable questionDeletable = new QuestionDeletableWriter();

        assertThatThrownBy(() -> questionDeletable.checkDeletable(question, NsUserTest.SANJIGI)).isInstanceOf(
                CannotDeleteException.class);
    }
}
