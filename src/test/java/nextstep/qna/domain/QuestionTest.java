package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");
    private Answer answer1;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void delete_성공() throws Exception {
        Q1.delete(NsUserTest.JAVAJIGI);
        assertThat(Q1.isDeleted()).isTrue();
        assertThat(Q1.todeleteHistories().size()).isEqualTo(1);

    }

    @Test
    void delete_다른_사람이_쓴_글() {
        Assertions.assertThatThrownBy(() -> {
            Q1.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void delete_성공_질문자_답변자_같음() throws Exception {
        Answer answer = new Answer(11L, NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");

        Q1.addAnswer(answer);
        Q1.delete(NsUserTest.JAVAJIGI);

        assertThat(Q1.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        assertThat(Q1.todeleteHistories().size()).isEqualTo(2);
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() throws Exception {
        Answer answer = new Answer(11L, NsUserTest.JAVAJIGI, QuestionTest.Q2, "Answers Contents1");

        Q2.addAnswer(answer);

        Assertions.assertThatThrownBy(() -> {
            Q2.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }
    
}
