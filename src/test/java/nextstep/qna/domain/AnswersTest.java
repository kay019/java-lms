package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nextstep.qna.domain.AnswerTest.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AnswersTest {

    private NsUser loginUser;

    @BeforeEach
    void setUp() {
        loginUser = NsUserTest.JAVAJIGI;
    }

    @Test
    @DisplayName("모든 답변 작성자가 동일한 지 확인 - true 케이스")
    void test_check_all_owner_true() {
        Answers answers = new Answers();
        answers.add(A1); // writer: JAVAJIGI(loginUser)
        answers.add(A3); // writer: JAVAJIGI(loginUser)

        assertThat(answers.areAllOwnedBy(loginUser)).isTrue();
    }

    @Test
    @DisplayName("모든 답변 작성자가 동일한 지 확인 - false 케이스")
    void test_check_all_owner_false() {
        Answers answers = new Answers();
        answers.add(A1); // writer: JAVAJIGI(loginUser)
        answers.add(A2); // writer: SANJIGI

        assertThat(answers.areAllOwnedBy(loginUser)).isFalse();
    }

    @Test
    @DisplayName("deleteAll 메소드 동작 확인 - DeleteHistory 객체 생성")
    void delete_all_test() {
        Answers answers = new Answers();
        answers.add(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Test1"));
        answers.add(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Test2"));
        List<DeleteHistory> histories = answers.deleteAll();
        assertThat(histories).hasSize(2);
    }
}
