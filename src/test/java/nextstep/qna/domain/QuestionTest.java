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

public class QuestionTest {

    public Question question;
    public Answer answer1;
    public Answer answer2;

    @BeforeEach
    void setUp() {
        question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        answer1 = new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents1");
        answer2 = new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents2");
    }

    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

    @Nested
    @DisplayName("Answer는 현재 질문이 삭제 가능한 상태인지 아닌지 판단할 수 있다.")
    class isSameUser {

        @Test
        @DisplayName("답변이 존재하지 않는 경우, 로그인한 사용자와 질문의 작성자가 같다면 List<DeleteHistory> 객체를 반환한다.")
        void loginAnswerIsSame() {
            List<DeleteHistory> deleteHistories = question.deleteBy(NsUserTest.JAVAJIGI);

            assertThat(question.isDeleted()).isTrue();
            assertThat(deleteHistories).hasSize(1);
            assertThat(deleteHistories).anySatisfy(history -> {
                assertThat(history.getContentType()).isEqualTo(ContentType.QUESTION);
                assertThat(history.getContentId()).isEqualTo(question.getId());
                assertThat(history.getDeletedBy()).isEqualTo(NsUserTest.JAVAJIGI);
            });
        }

        @Test
        @DisplayName("로그인한 사용자와 질문, 답변의 작성자가 같다면 List<DeleteHistory> 객체를 반환한다.")
        void loginAnswerQuestionIsSame() {
            question.addAnswer(answer1);
            question.addAnswer(answer2);
            List<DeleteHistory> deleteHistories = question.deleteBy(NsUserTest.JAVAJIGI);

            assertThat(question.isDeleted()).isTrue();
            assertThat(deleteHistories).hasSize(3);
            assertThat(deleteHistories).allSatisfy(
                history -> assertThat(history.getDeletedBy()).isEqualTo(NsUserTest.JAVAJIGI));
            assertThat(deleteHistories).extracting(DeleteHistory::getContentType)
                .containsExactlyInAnyOrder(ContentType.ANSWER, ContentType.ANSWER,
                    ContentType.QUESTION);
        }

        @Test
        @DisplayName("로그인한 사용자와 질문의 작성자가 다르다면 예외를 발생시킨다.")
        void loginAnswerIsNotSame() {
            assertThatThrownBy(() -> question.deleteBy(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class);
        }
    }
}
