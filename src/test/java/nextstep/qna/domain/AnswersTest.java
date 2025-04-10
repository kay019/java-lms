package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.qna.domain.ContentType.ANSWER;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;

class AnswersTest {

    @Test
    @DisplayName("답변을 추가하면 새로운 Answers 인스턴스를 반환한다")
    void addAnswer() {
        Answer answer1 = new Answer(1L, JAVAJIGI, "첫 번째 답변");
        Answer answer2 = new Answer(2L, JAVAJIGI, "두 번째 답변");

        Answers answers = new Answers(List.of(answer1));
        Answers newAnswers = answers.add(answer2);

        assertThat(newAnswers).isNotEqualTo(answers);
        assertThat(newAnswers.deleteAll(JAVAJIGI)).hasSize(2);
    }

    @Test
    @DisplayName("기존 Answers 인스턴스는 추가 후에도 변경되지 않는다")
    void immutabilityAfterAdd() {
        Answer originalAnswer = new Answer(1L, JAVAJIGI, "기존 답변");
        Answers originalAnswers = new Answers(List.of(originalAnswer));

        Answers newAnswers = originalAnswers.add(new Answer(2L, JAVAJIGI, "새 답변"));

        assertThat(originalAnswers).isNotEqualTo(newAnswers);
    }

    @Test
    @DisplayName("모든 답변을 삭제하면 각 답변의 삭제 이력이 수집된다")
    void deleteAllAnswers() {
        Answer answer1 = new Answer(1L, JAVAJIGI, "첫 번째 답변");
        Answer answer2 = new Answer(2L, JAVAJIGI, "두 번째 답변");

        Answers answers = new Answers(List.of(answer1, answer2));
        List<DeleteHistory> deleteHistories = answers.deleteAll(JAVAJIGI);

        List<DeleteHistory> expected = List.of(
            new DeleteHistory(ANSWER, answer1.getId(), JAVAJIGI, LocalDateTime.now()),
            new DeleteHistory(ANSWER, answer2.getId(), JAVAJIGI, LocalDateTime.now())
        );

        assertThat(deleteHistories).hasSize(2);
        assertThat(answers.deleteAll(JAVAJIGI)).hasSize(2);
        assertThat(deleteHistories).isEqualTo(expected);
    }
}
