package nextstep.qna.domain;

import org.junit.jupiter.api.Test;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    public void delete_권한체크() {
        assertThatThrownBy(() -> A1.validateForDelete(NsUserTest.SANJIGI))
            .hasMessageContaining("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @Test
    public void delete시_deleteHistory_체크() throws CannotDeleteException {
        DeleteHistory deleteHistory = A1.delete(NsUserTest.JAVAJIGI);
        assertThat(deleteHistory).isNotNull();
        assertThat(deleteHistory.getContentId()).isEqualTo(A1.getId());
        assertThat(deleteHistory.getDeletedBy()).isEqualTo(NsUserTest.JAVAJIGI);
    }
}
