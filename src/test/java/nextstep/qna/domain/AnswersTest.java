package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.time.LocalDateTime;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnswersTest {

    @Test
    @DisplayName("빈 Answers 객체를 생성할 수 있다")
    void createEmptyAnswers() {
        Answers answers = new Answers();

        assertThat(answers.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("답변을 추가할 수 있다")
    void addAnswer() {
        Answers answers = new Answers();
        Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용");

        answers.add(answer);

        assertThat(answers.isEmpty()).isFalse();
        assertThat(answers.contains(answer)).isTrue();
    }

    @Test
    @DisplayName("모든 답변이 삭제되었는지 확인할 수 있다")
    void isAllDeleted() {
        // given
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용1");
        Answer answer2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용2");
        Answers answers = new Answers(List.of(answer1, answer2));

        // when
        answer1.delete();

        // then
        assertThat(answers.isAllDeleted()).isFalse();

        // when
        answer2.delete();

        // then
        assertThat(answers.isAllDeleted()).isTrue();
    }

    @Test
    @DisplayName("다른 소유자가 있는지 확인할 수 있다")
    void hasOtherOwner() {
        // given
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용1");
        Answer answer2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "내용2");
        Answers answers = new Answers(List.of(answer1, answer2));

        // then
        assertThat(answers.hasOtherOwner(NsUserTest.JAVAJIGI)).isTrue();
        assertThat(answers.hasOtherOwner(NsUserTest.SANJIGI)).isTrue();

        // when
        Answers singleOwnerAnswers = new Answers(List.of(answer1));

        // then
        assertThat(singleOwnerAnswers.hasOtherOwner(NsUserTest.JAVAJIGI)).isFalse();
        assertThat(singleOwnerAnswers.hasOtherOwner(NsUserTest.SANJIGI)).isTrue();
    }

    @Test
    @DisplayName("모든 답변을 삭제할 수 있다")
    void deleteAll() throws CannotDeleteException {
        // given
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용1");
        Answer answer2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용2");
        Answers answers = new Answers(List.of(answer1, answer2));

        // when
        answers.deleteAll(NsUserTest.JAVAJIGI);

        // then
        assertThat(answers.isAllDeleted()).isTrue();
        assertThat(answer1.isDeleted()).isTrue();
        assertThat(answer2.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 이력을 생성할 수 있다")
    void createDeleteHistories() {
        // given
        Answer answer1 = new Answer(1L, NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용1");
        Answer answer2 = new Answer(2L, NsUserTest.SANJIGI, QuestionTest.Q1, "내용2");
        Answers answers = new Answers(List.of(answer1, answer2));
        
        // when
        answer1.delete();
        answer2.delete();
        DeleteHistories deleteHistories = answers.createDeleteHistories();
        
        // then
        assertThat(deleteHistories.size()).isEqualTo(2);
        
        List<DeleteHistory> histories = deleteHistories.getHistories();
        DeleteHistory expected1 = new DeleteHistory(ContentType.ANSWER, answer1.getId(), NsUserTest.JAVAJIGI, LocalDateTime.now());
        DeleteHistory expected2 = new DeleteHistory(ContentType.ANSWER, answer2.getId(), NsUserTest.SANJIGI, LocalDateTime.now());
        
        assertThat(histories).hasSize(2);
        assertThat(histories).contains(expected1, expected2);
    }

    @Test
    @DisplayName("특정 답변이 포함되어 있는지 확인할 수 있다")
    void contains() {
        // given
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용1");
        Answer answer2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용2");
        Answers answers = new Answers(List.of(answer1));

        // then
        assertThat(answers.contains(answer1)).isTrue();
        assertThat(answers.contains(answer2)).isFalse();
    }

    @Test
    @DisplayName("답변의 개수를 확인할 수 있다")
    void size() {
        // 빈 컬렉션
        Answers emptyAnswers = new Answers();
        assertThat(emptyAnswers.size()).isEqualTo(0);

        // 단일 요소 컬렉션
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용1");
        Answers singleAnswers = new Answers(List.of(answer1));
        assertThat(singleAnswers.size()).isEqualTo(1);

        // 다중 요소 컬렉션
        Answer answer2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용2");
        Answers multipleAnswers = new Answers(List.of(answer1, answer2));
        assertThat(multipleAnswers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("다른 사람이 작성한 답변이 있으면 삭제할 수 없다")
    void deleteAll_withOtherUserAnswers() {
        // given
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용1");
        Answer answer2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "내용2");
        Answers answers = new Answers(List.of(answer1, answer2));

        // then
        assertThatThrownBy(() -> answers.deleteAll(NsUserTest.JAVAJIGI))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessageContaining("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다");
    }

    @Test
    @DisplayName("자신이 작성한 답변만 있으면 삭제할 수 있다")
    void deleteAll_withOnlyOwnAnswers() throws CannotDeleteException {
        // given
        Answer answer1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용1");
        Answer answer2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "내용2");
        Answers answers = new Answers(List.of(answer1, answer2));

        // when
        answers.deleteAll(NsUserTest.JAVAJIGI);

        // then
        assertThat(answers.isAllDeleted()).isTrue();
        assertThat(answer1.isDeleted()).isTrue();
        assertThat(answer2.isDeleted()).isTrue();
    }
}
