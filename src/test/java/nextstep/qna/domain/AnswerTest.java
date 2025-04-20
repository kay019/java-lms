package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @DisplayName("isDeletable 테스트, 작성자가 같아야 삭제 가능")
    @ParameterizedTest
    @MethodSource
    public void deletableTest(NsUser user, boolean result) throws Exception {
        assertThat(A1.isDeletable(user))
                .isEqualTo(result);
    }

    @DisplayName("deleteBy 성공")
    @Test
    public void successDeleteBy() throws Exception {
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        answer1.deleteBy(NsUserTest.JAVAJIGI);
        assertThat(answer1.isDeleted())
                .isTrue();
    }

    @DisplayName("deleteBy 실패")
    @Test
    public void failedDeleteBy() throws Exception {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        assertThatThrownBy(() -> answer.deleteBy(NsUserTest.SANJIGI))
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

    private static Stream<Arguments> deletableTest() {
        return Stream.of(
                Arguments.of(NsUserTest.JAVAJIGI, true),
                Arguments.of(NsUserTest.SANJIGI, false)
        );
    }
}
