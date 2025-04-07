package nextstep.qna.domain;

import static nextstep.qna.domain.AnswerTest.A1;
import static nextstep.qna.domain.AnswerTest.A2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

class AnswersTest {
    @Test
    void createTest() {
        Answers answers = new Answers(List.of(A1, A2));

        assertThat(answers).isEqualTo(new Answers(List.of(A1, A2)));
    }

    @Test
    void checkOwnersTest() {
        Answers answers = new Answers(List.of(A1));

        assertThatNoException().isThrownBy(() -> answers.checkOwners(NsUserTest.JAVAJIGI));
    }

    @Test
    void checkOwnersExceptionTest() {
        Answers answers = new Answers(List.of(A1, A2));

        assertThatThrownBy(() -> answers.checkOwners(NsUserTest.JAVAJIGI)).isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}