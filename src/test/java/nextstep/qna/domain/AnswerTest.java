package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    @DisplayName("질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.")
    void deleteAnswerThrowTest() {
        //given
        Answer a1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");

        //when & then
        Assertions.assertThatThrownBy(() -> a1.markAsDeletedBy(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageContaining("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");

    }
}
