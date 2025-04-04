package nextstep.qna.domain;

import org.junit.jupiter.api.Test;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class AnswerTest {
    public Answer a1 = createA1();

    @Test
    void 삭제하면_deleted가_true가_되고_DeleteHistory를_응답한다() {
        NsUser loginUser = NsUserTest.JAVAJIGI;
        DeleteHistory deleteHistory = a1.delete(loginUser);
        assertThat(a1.isDeleted()).isTrue();

        assertThat(deleteHistory.getContentType()).isEqualTo(ContentType.ANSWER);
        assertThat(deleteHistory.getContentId()).isEqualTo(a1.getId());
        assertThat(deleteHistory.getDeletedBy()).isEqualTo(loginUser.getId());
        assertThat(deleteHistory.getCreatedDate()).isNotNull();
    }

    @Test
    void 본인이_작성한_Answer가_아니면_삭제할_수_없다() {
        NsUser loginUser = NsUserTest.SANJIGI;
        CannotDeleteException e = catchThrowableOfType(
            () -> a1.delete(loginUser), CannotDeleteException.class);
        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    public static Answer createA1() {
        return new Answer(NsUserTest.JAVAJIGI, QuestionTest.createQ1(), "Answers Contents1");
    }

    public static Answer createA2() {
        return new Answer(NsUserTest.SANJIGI, QuestionTest.createQ1(), "Answers Contents2");
    }
}
