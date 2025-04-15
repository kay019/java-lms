package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @DisplayName("isDeletable 테스트, 작성자가 같아야 삭제 가능")
    @Test
    public void deletableTest() throws Exception {
        assertThat(A1.isDeletable(NsUserTest.JAVAJIGI))
                .isTrue();

        assertThat(A1.isDeletable(NsUserTest.SANJIGI))
                .isFalse();
    }

    @DisplayName("deleteBy 테스트")
    @Test
    public void deleteBy() throws Exception {
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        answer1.deleteBy(NsUserTest.JAVAJIGI);
        assertThat(answer1.isDeleted())
                .isTrue();

        Answer answer2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        assertThatThrownBy(() -> answer2.deleteBy(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageStartingWith("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @DisplayName("DeleteHistory 생성 테스트")
    @Test
    public void createDeleteHistory() throws Exception {
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        answer1.deleteBy(NsUserTest.JAVAJIGI);
        assertThat(answer1.isDeleted())
                .isTrue();

        assertThat(answer1.getDeleteHistory())
                .isEqualTo(new DeleteHistory(ContentType.ANSWER, null, NsUserTest.JAVAJIGI, LocalDateTime.now()));

        Answer answer2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        assertThatThrownBy(() -> answer2.deleteBy(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageStartingWith("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");

        assertThat(answer2.getDeleteHistory())
                .isNull();
    }
}
