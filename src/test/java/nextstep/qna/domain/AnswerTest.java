package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    @Test
    void 삭제_성공() {
        Answer a = new Answer(NsUserTest.JAVAJIGI, new Question(), "Answers Contents1");
        a.delete();
        assertThat(a.isDeleted()).isTrue();
    }
}
