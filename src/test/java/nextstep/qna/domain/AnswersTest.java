package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class AnswersTest {

    Answers answers = new Answers();
    Answer answer;

    @BeforeEach
    void setUp(){
        answer = new Answer(11L, NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        answers.add(answer);
    }

    @Test
    void add(){
        assertThat(answers).hasFieldOrPropertyWithValue("answers", List.of(answer));
    }

    @Test
    void isOwner_답변작성자맞음() throws CannotDeleteException {
        assertThat(answers.isOwner(NsUserTest.JAVAJIGI)).isTrue();
    }

    @Test
    void isOwner_답변작성자아님() throws CannotDeleteException {
        assertThat(answers.isOwner(NsUserTest.SANJIGI)).isFalse();
    }

    @Test
    void delete() {
        DeleteHistories deleteHistories = new DeleteHistories();
        answers.delete(deleteHistories);

        assertThat(answer.isDeleted()).isTrue();
        assertThat(deleteHistories.getDeleteHistories().size()).isEqualTo(1);
    }
}
