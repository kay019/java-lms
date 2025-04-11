package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class AnswersTest {

    @Test
    void 모든_답변작성자가_질문답변자와_같은_경우_삭제할_수_있다() {
        Answers answers = new Answers(List.of(AnswerTest.A1, AnswerTest.A1));
        answers.deleteAll(NsUserTest.JAVAJIGI);
        answers.getAnswers().forEach(answer -> {
            assertThat(answer.isDeleted()).isTrue();
        });
    }

    @Test
    void 모든_답변작성자가_질문답변자와_다른_경우_삭제할_수_없다() {
        Answers answers = new Answers(List.of(AnswerTest.A1, AnswerTest.A2));
        assertThatThrownBy(() -> answers.deleteAll(NsUserTest.JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class);
    }

}
