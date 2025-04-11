package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.qna.CannotDeleteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.users.domain.NsUserTest;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    @DisplayName("내가 쓴 질문은 삭제할 수 있다.")
    void testDelete() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

        assertThat(question.isDeleted()).isFalse();
        question.delete(NsUserTest.JAVAJIGI);
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("다른 사람이 쓴 질문은 삭제할 수 없다.")
    void testDeleteWithSameWriter() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

        assertThatThrownBy(() -> {
            question.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

}
