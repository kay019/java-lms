package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteHistoryTest {
    @Test
    void 질문으로_생성() {
        Question q = new Question(1L, NsUserTest.JAVAJIGI, "title", "contents");
        DeleteHistory deleteHistory = new DeleteHistory(q);
        assertThat(deleteHistory.equals(
                new DeleteHistory(ContentType.QUESTION, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now()))
        ).isTrue();
    }

    @Test
    void 답변으로_생성() {
        Answer a = new Answer(2L, NsUserTest.JAVAJIGI, new Question(), "contents");
        DeleteHistory deleteHistory = new DeleteHistory(a);
        assertThat(deleteHistory.equals(
                new DeleteHistory(ContentType.ANSWER, 2L, NsUserTest.JAVAJIGI, LocalDateTime.now()))
        ).isTrue();
    }
}
