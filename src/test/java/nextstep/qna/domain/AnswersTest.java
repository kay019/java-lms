package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AnswersTest {

    public Question question;
    public Answer answer1;
    public Answer answer2;
    public Answers answers = new Answers();

    @BeforeEach
    void setUp() {
        question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        answer1 = new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents1");
        answer2 = new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents2");
        answers.add(answer1);
        answers.add(answer2);
    }

    @Nested
    @DisplayName("Answers는 현재 답변들이 삭제 가능한 상태인지 아닌지 판단할 수 있다.")
    class isSameUser {

        @Test
        @DisplayName("로그인한 사용자와 답변들의 작성자가 모두 같다면 List<DeleteHistory> 객체를 반환한다.")
        void loginQuestionIsSame() {
            List<DeleteHistory> histories = answers.deleteBy(NsUserTest.JAVAJIGI);

            assertThat(histories).hasSize(2);
            assertThat(answer1.isDeleted()).isTrue();
            assertThat(answer2.isDeleted()).isTrue();
            assertThat(histories).extracting(DeleteHistory::getContentType)
                .containsExactlyInAnyOrder(ContentType.ANSWER, ContentType.ANSWER);
        }

        @Test
        @DisplayName("로그인한 사용자와 답변의 작성자 중 일치하지 않는 것이 있다면 예외를 발생시킨다.")
        void loginQuestionIsNotSame() {
            assertThatThrownBy(() -> {
                answers.deleteBy(NsUserTest.SANJIGI);
            }).isInstanceOf(CannotDeleteException.class);
        }
    }
}
