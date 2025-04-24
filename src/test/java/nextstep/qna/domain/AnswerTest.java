package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    @DisplayName("delete - Owner가 아닌 경우 삭제할 수 없다.")
    void deleteHistoryFailTest() {
        assertThatThrownBy( () ->
                A1.delete(NsUserTest.STRANGER)
        ).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("delete - Owner가 아닌 경우 deleteHistory를 가져올 수 없다.")
    void deleteHistorySuccessTest() throws CannotDeleteException {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        DeleteHistory expected = new DeleteHistory(
                ContentType.ANSWER,
                answer.getId(),
                answer.getWriter(),
                LocalDateTime.now()
        );
        assertThat(answer.delete(answer.getWriter())).isEqualTo(expected);
    }
}
