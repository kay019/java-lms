package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");


    @Test
    void delete() {
        Q1.delete();
        assertThat(Q1.isDeleted()).isTrue();
    }

    @Test
    void validateDelete_success() {
        assertThatCode(() -> {
            Q2.validateDelete(NsUserTest.SANJIGI);
        }).doesNotThrowAnyException();
    }

    @Test
    void validateDelete_fail() {
        assertThatThrownBy(() -> {
            Q2.validateDelete(NsUserTest.JAVAJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }
}
