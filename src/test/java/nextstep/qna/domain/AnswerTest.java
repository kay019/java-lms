package nextstep.qna.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static nextstep.qna.domain.ContentType.ANSWER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    @DisplayName("작성자가 답변을 삭제하면 deleted가 true로 설정되고 DeleteHistory가 반환된다")
    void delete_by_owner() throws CannotDeleteException {
        NsUser owner = new NsUser(1L, "ownerId", "password", "owner", "owner@test.com");
        Answer answer = new Answer(1L, owner, getTestQuestion(owner), "answer");
        DeleteHistory deleteHistory = answer.delete(owner);
        DeleteHistory expect = new DeleteHistory(ANSWER, 1L, owner, LocalDateTime.now());

        assertThat(answer.isDeleted()).isTrue();
        assertThat(deleteHistory).isEqualTo(expect);
    }

    @Test
    @DisplayName("작성자가 아닌 사용자가 답변을 삭제하면 CannotDeleteException이 발생한다")
    void delete_by_non_owner_throws_exception() {
        NsUser owner = new NsUser(1L, "ownerId", "password", "owner", "owner@test.com");
        Answer answer = new Answer(1L, owner, getTestQuestion(owner), "answer contents");

        NsUser otherUser = new NsUser(2L, "otherUserId", "password", "other", "other@test.com");
        assertThatThrownBy(() -> answer.delete(otherUser))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    private Question getTestQuestion(NsUser owner) {
        return new Question(owner, "title", "contents");
    }
}
