package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");


    @Test
    @DisplayName("validateDeletion - 직접 작성한 질문이 아니면 삭제할 수 없다.")
    void validateDeletionTest1() {
        assertThatThrownBy(() -> {
            Q1.validateDeletion(NsUserTest.STRANGER);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("validateDeletion - 다른 사람의 댓글이 존재하면 삭제할 수 없다.")
    void validateDeletionTest2() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title", "contents");
        question.addAnswer(new Answer(NsUserTest.STRANGER, question, "contents"));
        assertThatThrownBy(() -> {
            question.validateDeletion(NsUserTest.JAVAJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

}
