package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class AnswersTest {

    @Test
    @DisplayName("로그인 유저와 답변작성자가 동일한 경우 답변을 삭제할 수 있다.")
    void deleteAnswersTest() throws CannotDeleteException {
        //given
        NsUser loginUser = NsUserTest.JAVAJIGI;
        Answer a1 = new Answer(loginUser, QuestionTest.Q1, "Answers Contents1");
        Answers answers = new Answers();
        answers.add(a1);

        //when
        List<DeleteHistory> deleteHistories = answers.markAsDeletedBy(loginUser);

        //then
        Assertions.assertThat(deleteHistories)
                .hasSize(1)
                .extracting("contentId", "contentType")
                .containsExactly(
                        Tuple.tuple(a1.getId(), ContentType.ANSWER)
                );
    }


}