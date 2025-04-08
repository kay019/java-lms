package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswersTest {
    @Test
    void 삭제_이력() {
        Answers answers = new Answers(List.of(
                new Answer(1L, NsUserTest.JAVAJIGI, new Question(), "contents"),
                new Answer(2L, NsUserTest.JAVAJIGI, new Question(), "contents")
        ));
        DeleteHistories deleteHistories = answers.deleteAll();
        assertThat(deleteHistories).isEqualTo(
            new DeleteHistories(
                Set.of(
                    new DeleteHistory(ContentType.ANSWER, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now()),
                    new DeleteHistory(ContentType.ANSWER, 2L, NsUserTest.JAVAJIGI, LocalDateTime.now())
                )
            )
        );
    }
}
