package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nextstep.qna.domain.AnswerTest.A1;
import static nextstep.qna.domain.AnswerTest.A2;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class AnswersTest {

    Answers answers = new Answers();
    Answer A1;
    Answer A2;

    @BeforeEach
    void setUp(){
        A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");
        answers.add(A1);
    }

    @Test
    void add(){
        assertThat(answers).hasFieldOrPropertyWithValue("answers", List.of(A1));
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
    void delete_성공() throws CannotDeleteException {
        assertThat(answers.delete(NsUserTest.JAVAJIGI).size()).isEqualTo(1);
        assertThat(A1.isDeleted()).isTrue();
    }

    @Test
    void delete_답변작성자가아닌답변이포함() {
        answers.add(A2);

        assertThatThrownBy(() -> answers.delete(NsUserTest.JAVAJIGI)).isInstanceOf(CannotDeleteException.class);
        assertThat(A1.isDeleted()).isTrue();
        assertThat(A2.isDeleted()).isFalse();

    }
}
