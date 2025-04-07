package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @DisplayName("다른 사람의 답변이 있는 경우 예외를 발생시킨다.")
    @Test
    void checkOwnerExceptionTest() {
        assertThatThrownBy(() -> A1.checkOwner(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @DisplayName("질문자와 답변자가 모두 같은 경우 예외 발생하지 않는다.")
    @Test
    void checkOwnerTest() {
        assertThatNoException().isThrownBy(() -> A1.checkOwner(NsUserTest.JAVAJIGI));
    }
}
