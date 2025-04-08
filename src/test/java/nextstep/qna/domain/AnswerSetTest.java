package nextstep.qna.domain;

import java.util.List;

import org.junit.jupiter.api.Test;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;

import static nextstep.qna.domain.AnswerTest.A1;
import static nextstep.qna.domain.AnswerTest.A2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerSetTest {

    @Test
    public void delete_권한체크(){
        AnswerSet answerSet = new AnswerSet(A1, A2);

        assertThatThrownBy(() ->
            answerSet.validateForDelete(NsUserTest.JAVAJIGI))
            .hasMessageContaining("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @Test
    public void delete_테스트() throws CannotDeleteException {
        AnswerSet answerSet = new AnswerSet(A1, A1);

        List<DeleteHistory> deleteHistories = answerSet.delete(NsUserTest.JAVAJIGI);
        assertThat(deleteHistories).hasSize(2);
    }
}
