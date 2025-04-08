package nextstep.qna.domain;

import java.util.List;

import org.junit.jupiter.api.Test;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    public void delete_권한체크_테스트(){
        assertThatThrownBy(() -> Q1.validateForDelete(NsUserTest.SANJIGI))
            .hasMessageContaining("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    public void delete시_DeleteHistory체크() throws CannotDeleteException {
        List<DeleteHistory> deleteHistories =  Q1.delete(NsUserTest.JAVAJIGI);
        assertThat(deleteHistories).hasSize(1);
    }
}
