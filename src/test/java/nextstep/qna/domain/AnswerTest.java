package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1"); // login user
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2"); // diff user
    public static final Answer A3 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents2"); // same user

    @Test
    @DisplayName("delete 호출 전/후 isDeleted() 값 확인 &  delete() 메소드 리턴값 DeleteHistory 확인")
    void delete_test() {
        assertThat(A1.isDeleted()).isFalse();
        assertThat(A1.delete()).isInstanceOf(DeleteHistory.class);
        assertThat(A1.isDeleted()).isTrue();
    }
}
