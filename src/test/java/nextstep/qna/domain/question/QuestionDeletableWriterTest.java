package nextstep.qna.domain.question;

import nextstep.qna.domain.Question;
import nextstep.qna.domain.qustion.QuestionDeletable;
import nextstep.qna.domain.qustion.QuestionDeletableWriter;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionDeletableWriterTest {

    @Test
    void 작성자라_삭제가능() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        QuestionDeletable questionDeletable = new QuestionDeletableWriter();

        boolean result = questionDeletable.deletable(question, NsUserTest.JAVAJIGI);

        assertThat(result).isTrue();
    }

    @Test
    void 작성자가_아니라_삭제불가() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        QuestionDeletable questionDeletable = new QuestionDeletableWriter();

        boolean result = questionDeletable.deletable(question, NsUserTest.SANJIGI);

        assertThat(result).isFalse();
    }
}
