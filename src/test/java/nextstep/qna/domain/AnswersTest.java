package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswersTest {
    @Test
    void delete() throws CannotDeleteException {
        Answers removable = new Answers();
        Answers notRemovable = new Answers();

        removable.add(AnswerTest.A1);
        removable.add(AnswerTest.A1);
        notRemovable.add(AnswerTest.A1);
        notRemovable.add(AnswerTest.A2);

        assertThat(removable.delete(NsUserTest.JAVAJIGI).size()).isEqualTo(2);
        assertThatThrownBy(() -> notRemovable.delete(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class);

    }
}
