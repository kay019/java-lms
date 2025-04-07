package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @DisplayName("다른 사람이 쓴 글은 삭제할 수 없습니다.")
    @Test
    public void delete_다른_사람이_쓴_글() throws Exception {
        assertThatThrownBy(() -> {
            Q1.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }
}
