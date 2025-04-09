package nextstep.qna.domain;

import nextstep.qna.exception.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");
    
    private Question question;
    private Answer answer;
    
    @BeforeEach
    public void setUp() {
        question = new Question(1L, NsUserTest.JAVAJIGI, "title1", "contents1");
        answer = new Answer(11L, NsUserTest.JAVAJIGI, question, "Answers Contents1");
        question.addAnswer(answer);
    }
    
    @Test
    public void delete_성공() throws CannotDeleteException {
        List<DeleteHistory> deleteHistories = question.delete(NsUserTest.JAVAJIGI);

        assertThat(question.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        assertThat(deleteHistories).hasSize(2);
    }
    
    @Test
    public void delete_다른_사람이_쓴_글() {
        assertThatThrownBy(() -> question.delete(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class);
    }
    
    @Test
    public void delete_답변_중_다른_사람이_쓴_글() {
        Answer otherAnswer = new Answer(12L, NsUserTest.SANJIGI, question, "Answers Contents2");
        question.addAnswer(otherAnswer);

        assertThatThrownBy(() -> question.delete(NsUserTest.JAVAJIGI)).isInstanceOf(CannotDeleteException.class);
    }
}
