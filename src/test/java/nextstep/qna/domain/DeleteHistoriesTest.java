package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class DeleteHistoriesTest {
    @Test
    void concat() throws CannotDeleteException {
        DeleteHistories deleteHistories_1 = QuestionTest.Q1.delete(NsUserTest.JAVAJIGI);
        DeleteHistories deleteHistories_2 = QuestionTest.Q1.delete(NsUserTest.JAVAJIGI);

        assertThat(deleteHistories_1.concat(deleteHistories_2).size()).isEqualTo(2);
    }

    @Test
    void add() throws CannotDeleteException {
        DeleteHistories deleteHistories = QuestionTest.Q1.delete(NsUserTest.JAVAJIGI);
        DeleteHistory deleteHistory = AnswerTest.A1.delete(NsUserTest.JAVAJIGI);

        assertThat(deleteHistories.add(deleteHistory).size()).isEqualTo(2);
    }
}
