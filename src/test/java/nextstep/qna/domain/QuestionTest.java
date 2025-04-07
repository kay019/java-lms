package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    @Test
    void 삭제_성공() throws CannotDeleteException {
        Question q = new Question(NsUserTest.JAVAJIGI, "title", "contents");
        q.delete(NsUserTest.JAVAJIGI);
        assertThat(q.isDeleted()).isTrue();
    }

    @Test
    void 삭제_실패_질문_권한_없음() {
        Question q = new Question(NsUserTest.JAVAJIGI, "title", "contents");
        assertThatThrownBy(() ->
                q.delete(NsUserTest.SANJIGI)
        ).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void 삭제_성공_답변_권한_있음() throws CannotDeleteException {
        Question q = new Question(NsUserTest.JAVAJIGI, "title", "contents");
        q.addAnswer(new Answer(NsUserTest.JAVAJIGI, q, ""));
        q.delete(NsUserTest.JAVAJIGI);
        assertThat(q.isDeleted()).isTrue();
    }

    @Test
    void 삭제_실패_답변_권한_없음() {
        Question q = new Question(NsUserTest.JAVAJIGI, "title", "contents");
        q.addAnswer(new Answer(NsUserTest.SANJIGI, q, ""));
        assertThatThrownBy(() ->
                q.delete(NsUserTest.JAVAJIGI)
        ).isInstanceOf(CannotDeleteException.class);
    }
}
