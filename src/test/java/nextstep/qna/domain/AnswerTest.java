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
    @DisplayName("deleteHistory - 삭제되지 않은 정답은 deleteHistory를 가져올 수 없다.")
    void deleteHistoryFailTest() throws CannotDeleteException {
        assertThatThrownBy( () ->
                A1.deleteHistory(A1.getWriter())
        ).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("deleteHistory - 삭제처리 된 정답은 deleteHistory를 가져올 수 있다.")
    void deleteHistorySuccessTest() throws CannotDeleteException {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        answer.markAsDeleted();
        DeleteHistory expected = new DeleteHistory(
                ContentType.ANSWER,
                answer.getId(),
                NsUserTest.JAVAJIGI,
                LocalDateTime.now()
        );
        assertThat(answer.deleteHistory(answer.getWriter())).isEqualTo(expected);
    }
}
