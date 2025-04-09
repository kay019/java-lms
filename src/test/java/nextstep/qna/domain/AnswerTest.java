package nextstep.qna.domain;

import nextstep.qna.exception.NotFoundException;
import nextstep.qna.exception.UnAuthorizedException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {

    private Question question;
    private Answer answer;
    
    @BeforeEach
    public void setUp() {
        question = new Question(1L, NsUserTest.JAVAJIGI, "title1", "contents1");
        answer = new Answer(11L, NsUserTest.JAVAJIGI, question, "Answers Contents1");
    }
    
    @Test
    @DisplayName("답변 생성 테스트")
    public void creation() {
        assertThat(answer).isNotNull();
        assertThat(answer.getWriter()).isEqualTo(NsUserTest.JAVAJIGI);
        assertThat(answer.getContents()).isEqualTo("Answers Contents1");
        assertThat(answer.isDeleted()).isFalse();
    }
    
    @Test
    @DisplayName("답변 생성 시 작성자가 null인 경우")
    public void creation_null_writer() {
        assertThatThrownBy(() -> {
            new Answer(null, question, "Answers Contents1");
        }).isInstanceOf(UnAuthorizedException.class);
    }
    
    @Test
    @DisplayName("답변 생성 시 질문이 null인 경우")
    public void creation_null_question() {
        assertThatThrownBy(() -> {
            new Answer(NsUserTest.JAVAJIGI, null, "Answers Contents1");
        }).isInstanceOf(NotFoundException.class);
    }
    
    @Test
    @DisplayName("답변 삭제 상태 변경 테스트")
    public void delete() {
        answer.delete(new NsUser());
        assertThat(answer.isDeleted()).isTrue();
    }
    
    @Test
    @DisplayName("작성자 일치 여부 확인 - 일치하는 경우")
    public void isOwner_true_case() {
        assertThat(answer.isOwner(NsUserTest.JAVAJIGI)).isTrue();
    }
    
    @Test
    @DisplayName("작성자 일치 여부 확인 - 일치하지 않는 경우")
    public void isOwner_false_case() {
        assertThat(answer.isOwner(NsUserTest.SANJIGI)).isFalse();
    }
}
