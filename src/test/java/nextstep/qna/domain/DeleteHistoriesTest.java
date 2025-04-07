package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteHistoriesTest {
    @Test
    void 질문으로_생성() {
        Question q = new Question(1L, NsUserTest.JAVAJIGI, "title", "contents");
        q.addAnswer(new Answer(2L, NsUserTest.JAVAJIGI, q, "answer contents"));
        DeleteHistories deleteHistories = DeleteHistories.from(q);
        assertThat(deleteHistories).isEqualTo(
            new DeleteHistories(
                List.of(
                    new DeleteHistory(ContentType.QUESTION, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now()),
                    new DeleteHistory(ContentType.ANSWER, 2L, NsUserTest.JAVAJIGI, LocalDateTime.now())
                )
            )
        );
    }
}
