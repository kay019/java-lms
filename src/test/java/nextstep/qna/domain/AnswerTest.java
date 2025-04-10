package nextstep.qna.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.qna.CannotDeleteException;

import static nextstep.qna.domain.ContentType.ANSWER;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static nextstep.users.domain.NsUserTest.SANJIGI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(new AnswerInfo(1L, JAVAJIGI, "Answers Contents1"));

    @Test
    @DisplayName("작성자가 답변을 삭제하면 deleted가 true로 설정되고 DeleteHistory가 반환된다")
    void delete_by_owner() throws CannotDeleteException {
        DeleteHistory deleteHistory = A1.delete(JAVAJIGI);
        DeleteHistory expect = new DeleteHistory(ANSWER, 1L, JAVAJIGI, LocalDateTime.now());

        assertThat(A1.isDeleted()).isTrue();
        assertThat(deleteHistory).isEqualTo(expect);
    }

    @Test
    @DisplayName("작성자가 아닌 사용자가 답변을 삭제하면 CannotDeleteException이 발생한다")
    void delete_by_non_owner_throws_exception() {
        assertThatThrownBy(() -> A1.delete(SANJIGI))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
