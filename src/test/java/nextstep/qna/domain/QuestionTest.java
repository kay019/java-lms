package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    void delete() throws CannotDeleteException {
        assertThat(Q1.delete(NsUserTest.JAVAJIGI).size()).isEqualTo(1);
        assertThatThrownBy(() -> Q1.delete(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class);
    }
}
