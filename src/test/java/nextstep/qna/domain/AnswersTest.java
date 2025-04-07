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
    public static final Answers ANSWERS = new Answers(List.of(A1, A2));

    @Test
    void createTest() {
        assertThat(new Answers(List.of(A1, A2))).isEqualTo(ANSWERS);
    }

    @Test
    void addAnswerTest() {
        Answers sut = new Answers(List.of(A1));

        sut = sut.addAnswer(A2);

        assertThat(sut).isEqualTo(new Answers(List.of(A1, A2)));
    }

    @Test
    void checkOwnersTest() {
        Answers answers = new Answers(List.of(A1));

        assertThatNoException().isThrownBy(() -> answers.checkOwners(NsUserTest.JAVAJIGI));
    }

    @Test
    void checkOwnersExceptionTest() {
        assertThatThrownBy(() -> ANSWERS.checkOwners(NsUserTest.JAVAJIGI)).isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @Test
    void createHistoriesTest() {
        List<DeleteHistory> histories = ANSWERS.createHistories();

        assertThat(histories).isEqualTo(List.of(A1.createDeleteHistory(), A2.createDeleteHistory()));
    }
}