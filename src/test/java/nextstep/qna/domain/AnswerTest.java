package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    @DisplayName("답변을 삭제할 때 삭제 상태(deleted)를 변경한다.")
    void testDelete() {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");

        assertThat(answer.isDeleted()).isFalse();
        answer.delete();
        assertThat(answer.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 이력을 조회할 수 있다.")
    void testDeleteHistory() {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");

        DeleteHistory deleteHistory = answer.getDeleteHistory();
        assertThat(deleteHistory.getContentType()).isEqualTo(ContentType.ANSWER);
        assertThat(deleteHistory.getContentId()).isEqualTo(answer.getId());
        assertThat(deleteHistory.getDeletedBy()).isEqualTo(NsUserTest.JAVAJIGI);
    }
}
