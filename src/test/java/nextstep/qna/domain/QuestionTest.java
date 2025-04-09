package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    void 질문자와_사용자가_같은경우_답변없음_삭제() {
        Question question = new Question(NsUserTest.SANJIGI, "제목", "내용");

        assertThatCode(() -> question.delete(NsUserTest.SANJIGI)).doesNotThrowAnyException();
    }

    @Test
    void 질문자와_사용자_같고_답변과_사용자_같음_삭제() {
        Question question = new Question(NsUserTest.JAVAJIGI, "제목", "내용");
        Answer answer = new Answer(NsUserTest.JAVAJIGI, question, "답변");
        question.addAnswer(answer);

        assertThatCode(() -> question.delete(NsUserTest.JAVAJIGI)).doesNotThrowAnyException();
    }

    @Test
    void 질문자와_사용자_같고_답변과_사용자_다르면_삭제X() {
        Question question = new Question(NsUserTest.JAVAJIGI, "제목", "내용");
        Answer answer = new Answer(NsUserTest.SANJIGI, question, "답변");
        question.addAnswer(answer);

        assertThatIllegalArgumentException().isThrownBy(() -> question.delete(NsUserTest.JAVAJIGI));
    }
}
