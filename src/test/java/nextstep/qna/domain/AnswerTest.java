package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AnswerTest {

    public Answer answer;

    @BeforeEach
    void setUp() {
        answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    }

    @Nested
    @DisplayName("Answer는 현재 답변이 삭제 가능한 상태인지 아닌지 판단할 수 있다.")
    class isSameUser {

        @Test
        @DisplayName("로그인한 사용자와 답변의 작성자가 같다면 DeleteHistory 객체를 반환한다.")
        void loginQuestionIsSame() {
            DeleteHistory history = answer.deleteBy(NsUserTest.JAVAJIGI);
            assertThat(answer.isDeleted()).isTrue();
            assertThat(history).isNotNull();
            assertThat(history.getContentType()).isEqualTo(ContentType.ANSWER);
            assertThat(history.getContentId()).isEqualTo(answer.getId());
            assertThat(history.getDeletedBy()).isEqualTo(NsUserTest.JAVAJIGI);
        }

        @Test
        @DisplayName("로그인한 사용자와 답변의 작성자가 다르다면 예외를 발생시킨다.")
        void loginQuestionIsNotSame() {
            assertThatThrownBy(() -> {
                answer.deleteBy(NsUserTest.SANJIGI);
            }).isInstanceOf(CannotDeleteException.class);
        }
    }
}
