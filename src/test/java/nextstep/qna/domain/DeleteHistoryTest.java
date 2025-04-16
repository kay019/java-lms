package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteHistoryTest {

    @Test
    void forQuestion(){
        assertThat(DeleteHistory.forQuestion(1L, NsUserTest.JAVAJIGI).getContentType()).isEqualTo(ContentType.QUESTION);
    }

    @Test
    void forAnswer(){
        assertThat(DeleteHistory.forAnswer(1L, NsUserTest.JAVAJIGI).getContentType()).isEqualTo(ContentType.ANSWER);
    }
}
