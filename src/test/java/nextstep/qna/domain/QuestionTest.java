package nextstep.qna.domain;

import java.util.List;

import org.junit.jupiter.api.Test;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class QuestionTest {
    public Question q1 = createQ1();

    @Test
    void 삭제하면_deleted가_true가_되고_DeleteHistories를_응답한다() {
        NsUser loginUser = NsUserTest.JAVAJIGI;
        Answer answer = AnswerTest.createA1();
        q1.addAnswer(answer);
        DeleteHistories deleteHistories = q1.delete(loginUser);

        DeleteHistory questionHistory = deleteHistories.getQuestionHistory();
        List<DeleteHistory> answerHistories = deleteHistories.getAnswerHistories();

        assertThat(q1.isDeleted()).isTrue();

        assertThat(deleteHistories.getAllDeleteHistories()).hasSize(2);
        assertThat(questionHistory).isNotNull();
        assertThat(answerHistories).hasSize(1);

        assertThat(questionHistory.getContentType()).isEqualTo(ContentType.QUESTION);
        assertThat(questionHistory.getContentId()).isEqualTo(q1.getId());
        assertThat(questionHistory.getDeletedBy()).isEqualTo(loginUser.getId());
        assertThat(questionHistory.getCreatedDate()).isNotNull();

        DeleteHistory answerHistory = answerHistories.get(0);
        assertThat(answerHistory.getContentType()).isEqualTo(ContentType.ANSWER);
        assertThat(answerHistory.getContentId()).isEqualTo(answer.getId());
        assertThat(answerHistory.getDeletedBy()).isEqualTo(loginUser.getId());
        assertThat(answerHistory.getCreatedDate()).isNotNull();
    }

    @Test
    void 본인이_작성한_Question이_아니면_삭제할_수_없다() {
        NsUser loginUser = NsUserTest.SANJIGI;
        CannotDeleteException e = catchThrowableOfType(
            () -> q1.delete(loginUser), CannotDeleteException.class);
        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    void 답변이_있는_경우_모든_답변의_작성자가_본인이여야_삭제할_수_있다() {
        NsUser loginUser = NsUserTest.JAVAJIGI;

        q1.addAnswer(AnswerTest.createA1());
        q1.addAnswer(AnswerTest.createA2());

        CannotDeleteException e = catchThrowableOfType(
            () -> q1.delete(loginUser), CannotDeleteException.class);
        assertThat(e).isNotNull();
        assertThat(e.getMessage()).isEqualTo("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    public static Question createQ1() {
        return new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    }
}
