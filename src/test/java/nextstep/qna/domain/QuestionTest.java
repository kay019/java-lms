package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    @DisplayName("로그인 사용자와 질문한 사람이 다른 경우 삭제 불가능하다")
    void deleteQuestionThrowTest() {
        //given
        Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

        //when & then
        Assertions.assertThatThrownBy(() -> Q1.markAsDeletedBy(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageContaining("질문을 삭제할 권한이 없습니다.");

    }

    @Test
    @DisplayName("답변이 없는 경우 삭제가 가능하다.")
    void deleteQuestionWithoutAnswersTest() throws CannotDeleteException {
        //given
        Question Q3 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

        //when
        List<DeleteHistory> deleteHistories = Q3.markAsDeletedBy(NsUserTest.JAVAJIGI);

        //then
        Assertions.assertThat(deleteHistories).hasSize(1);
    }
}
