package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @DisplayName("deleteBy 성공")
    @Test
    public void successDeleteBy() throws Exception {
        Question question1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        question1.addAnswer(answer1);
        question1.deleteBy(NsUserTest.JAVAJIGI);
        assertThat(question1.isDeleted())
                .isTrue();
    }

    @DisplayName("deleteBy 실패, 작성자 아님")
    @Test
    public void failedDeleteBy1() throws Exception {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        assertThatThrownBy(() -> question.deleteBy(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageStartingWith("질문을 삭제할 권한이 없습니다.");
        assertThat(question.isDeleted())
                .isFalse();
    }

    @DisplayName("deleteBy 실패, 다른 사람 댓글 있음")
    @Test
    public void failedDeleteBy2() throws Exception {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents1");
        question.addAnswer(answer);
        assertThatThrownBy(() -> question.deleteBy(NsUserTest.JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageStartingWith("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        assertThat(question.isDeleted())
                .isFalse();
    }

    @DisplayName("DeleteHistory 생성 테스트")
    @Test
    public void createDeleteHistory() throws Exception {
        Question question1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        question1.addAnswer(answer1);
        question1.deleteBy(NsUserTest.JAVAJIGI);
        assertThat(question1.isDeleted())
                .isTrue();

        assertThat(question1.getDeleteHistories())
                .hasSize(2)
                .contains(new DeleteHistory(ContentType.QUESTION, 0L, NsUserTest.JAVAJIGI, LocalDateTime.now()),
                        new DeleteHistory(ContentType.ANSWER, null, NsUserTest.JAVAJIGI, LocalDateTime.now()));

        Question question2 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        assertThatThrownBy(() -> question2.deleteBy(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageStartingWith("질문을 삭제할 권한이 없습니다.");
        assertThat(question2.isDeleted())
                .isFalse();

        assertThat(question2.getDeleteHistories())
                .isEmpty();
    }
}
