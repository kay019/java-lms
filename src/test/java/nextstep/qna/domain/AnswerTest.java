package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");
    public Answers answers;

    @BeforeEach
    public void setUp() {
        this.answers = new Answers();
        answers.addAnswer(A1);
    }

    @Test
    public void testAnswer() throws CannotDeleteException {
        Answer answer = A1.delete(NsUserTest.JAVAJIGI);
        assertThat(answer).isEqualTo(A1);
        assertThat(answer.isDeleted()).isTrue();
    }

    @Test
    public void testAnswerFromOtherUser() {
        assertThatThrownBy(() -> A2.delete(NsUserTest.JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class);
    }
}
