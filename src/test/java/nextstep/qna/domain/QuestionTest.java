package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @DisplayName("질문 작성자가 아닌 경우 예외를 발생시킨다.")
    @Test
    void checkOwnerExceptionTest() {
        assertThatThrownBy(() -> Q1.checkOwner(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class)
                .hasMessage("질문을 삭제할 권한이 없습니다.");
    }

    @DisplayName("질문 작성자가 일치하는 경우 예외 발생하지 않는다.")
    @Test
    void checkOwnerTest() {
        assertThatNoException().isThrownBy(() -> Q1.checkOwner(NsUserTest.JAVAJIGI));
    }
}
