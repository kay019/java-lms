package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.qna.CannotDeleteException;

import static nextstep.qna.domain.ContentType.QUESTION;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static nextstep.users.domain.NsUserTest.SANJIGI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    private Question Q1;

    @BeforeEach
    void setUp() {
        Q1 = new Question(JAVAJIGI, "title1", "contents1");

        Answer answer1 = new Answer(1L, JAVAJIGI, "Answer 1");
        Answer answer2 = new Answer(2L, JAVAJIGI, "Answer 2");

        Q1.addAnswer(answer1);
        Q1.addAnswer(answer2);
    }

    @Test
    @DisplayName("작성자는 질문을 삭제할 수 있다.")
    void deleteBySelf() throws CannotDeleteException {
        DeleteHistory expected = new DeleteHistory(QUESTION, Q1.getId(), JAVAJIGI, LocalDateTime.now());
        List<DeleteHistory> deleteHistories = Q1.delete(JAVAJIGI, Q1.getId());

        assertThat(Q1.isDeleted()).isTrue();
        assertThat(deleteHistories).hasSize(3);

        DeleteHistory questionDeleteHistory = deleteHistories.get(0);
        assertThat(questionDeleteHistory).isEqualTo(expected);
    }

    @Test
    @DisplayName("작성자가 아닌 사용자는 질문을 삭제할 수 없다.")
    void deleteByOtherTest() {
        assertThatThrownBy(() -> Q1.delete(SANJIGI, Q1.getId()))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessage("질문을 삭제할 권한이 없습니다.");

        assertThat(Q1.isDeleted()).isFalse();
    }
}
