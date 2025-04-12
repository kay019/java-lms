package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    @DisplayName("질문 작성자만 삭제할 수 있다")
    void delete_success_if_writer () throws CannotDeleteException {
        Q1.delete(NsUserTest.JAVAJIGI);

        assertThat(Q1.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("질문 작성자가 아닌 유저의 삭제 요청 시 예외 처리하고 예외 메세지 확인")
    void delete_error_if_not_writer () {
        assertThatThrownBy(()-> Q1.delete(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class).hasMessage("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    @DisplayName("답변 없는 경우 삭제가 가능하다")
    void delete_success_if_no_answer() throws CannotDeleteException {
        Q1.delete(NsUserTest.JAVAJIGI);

        assertThat(Q1.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("답변이 있지만 모두 질문 작성자에 의해 작성된 경우 삭제가 가능하다")
    void delete_success_if_all_answers_by_writer () throws CannotDeleteException {
        Q1.addAnswer(AnswerTest.A1);  // Answer by writer (JAVAJIGI)
        Q1.addAnswer(AnswerTest.A3);  // Answer by writer (JAVAJIGI)

        List<DeleteHistory> deleteHistoryList = Q1.delete(NsUserTest.JAVAJIGI);

        assertThat(Q1.isDeleted()).isTrue();
        assertThat(deleteHistoryList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("질문 답변 중 질문 작성자가 아닌 다른 사람 답변이 있는 경우 예외처리한다")
    void delete_error_if_all_answers_not_by_writer () {
        Q1.addAnswer(AnswerTest.A1); // Answer by writer (JAVAJIGI)
        Q1.addAnswer(AnswerTest.A2); // Answer by not writer (SANGJIGI)

        assertThatThrownBy(()-> Q1.delete(NsUserTest.JAVAJIGI)).isInstanceOf(CannotDeleteException.class).hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
