package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswersTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    @DisplayName("다른 사림의 답변이 있는 글은 삭제할 수 없습니다.")
    @Test
    public void cannot_delete_question() {
        Answer answer = new Answer(11L, NsUserTest.JAVAJIGI, AnswersTest.Q1, "Answers Contents1");
        Answers answers = new Answers();
        answers.add(answer);

        assertThatThrownBy(() -> answers.deleteAnswers(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
