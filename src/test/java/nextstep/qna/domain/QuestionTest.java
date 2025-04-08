package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @DisplayName("다른 사람이 쓴 글은 삭제할 수 없습니다.")
    @Test
    public void delete_다른_사람이_쓴_글() throws Exception {
        assertThatThrownBy(() -> {
            Q1.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @DisplayName("다른 사람이 쓴 답변이 있는 글은 삭제할 수 없습니다.")
    @Test
    public void delete_답변_중_다른_사람이_쓴_글() throws Exception {
        Answer answer = new Answer(11L, NsUserTest.JAVAJIGI, QuestionTest.Q2, "Answers Contents2");
        Q2.addAnswer(answer);

        assertThatThrownBy(() -> {
            Q2.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @DisplayName("질문 삭제 시, deleteHistory에 추가됩니다.")
    @Test
    public void delete_성공_질문자_답변자_같음() throws Exception {
        Answer answer = new Answer(11L, NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        Q1.addAnswer(answer);

        List<DeleteHistory> deleteHistories = Q1.delete(NsUserTest.JAVAJIGI);

        assertThat(Q1.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        assertThat(deleteHistories).isEqualTo(Arrays.asList(
                new DeleteHistory(ContentType.QUESTION, Q1.getId(), Q1.getWriter(), LocalDateTime.now()),
                new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now()))
        );
    }
}
