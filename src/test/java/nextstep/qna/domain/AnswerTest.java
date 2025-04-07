package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    @Test
    void 삭제_성공() {
        Answer a = new Answer(NsUserTest.JAVAJIGI, new Question(), "Answers Contents1");
        a.delete();
        assertThat(a.isDeleted()).isTrue();
    }

    @Test
    void 삭제_이력() {
        Answer a = new Answer(1L, NsUserTest.JAVAJIGI, new Question(), "Answers Contents1");
        a.delete();
        assertThat(a.deleteHistory()).isEqualTo(
            new DeleteHistory(
                ContentType.ANSWER, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now()
            )
        );
    }
}
