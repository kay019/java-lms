package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void testAnswer() {
        assertThat(answers.hasAnswerFromOtherUser(NsUserTest.JAVAJIGI)).isFalse();
    }

    @Test
    public void testAnswerFromOtherUser() {
        assertThat(answers.hasAnswerFromOtherUser(NsUserTest.SANJIGI)).isTrue();
    }
}
