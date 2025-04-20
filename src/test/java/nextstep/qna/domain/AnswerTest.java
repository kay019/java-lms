package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    void delete() {
        A1.delete();
        assertThat(A1.isDeleted()).isTrue();
    }

    @Test
    void validateDelete_success() {
        assertThatCode(() -> {
            A2.validateDelete(NsUserTest.SANJIGI);
        }).doesNotThrowAnyException();
    }

    @Test
    void validateDelete_fail() {
        assertThatThrownBy(() -> {
            A2.validateDelete(NsUserTest.JAVAJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }
}
