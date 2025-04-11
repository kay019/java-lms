package nextstep.qna.service;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.ContentType;
import nextstep.qna.domain.DeleteHistory;
import nextstep.qna.domain.DeleteHistoryRepository;
import nextstep.qna.domain.Question;
import nextstep.qna.domain.Answer;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteHistoryServiceTest {
    @Mock
    private DeleteHistoryRepository deleteHistoryRepository;

    @InjectMocks
    private DeleteHistoryService deleteHistoryService;

    private Question question;

    @BeforeEach
    void setUp() throws CannotDeleteException {
        question = new Question(1L, NsUserTest.JAVAJIGI, "title1", "contents1");
        question.delete(NsUserTest.JAVAJIGI);
    }

    @Test
    @DisplayName("질문만 있는 경우 삭제 이력을 생성한다")
    void saveFrom_questionOnly() {
        // when
        deleteHistoryService.saveFrom(question);

        // then
        ArgumentCaptor<List<DeleteHistory>> captor = ArgumentCaptor.forClass(List.class);
        verify(deleteHistoryRepository).saveAll(captor.capture());
        
        List<DeleteHistory> deleteHistories = captor.getValue();
        assertThat(deleteHistories).hasSize(1);
        assertThat(deleteHistories.get(0).getContentType()).isEqualTo(ContentType.QUESTION);
        assertThat(deleteHistories.get(0).getContentId()).isEqualTo(question.getId());
        assertThat(deleteHistories.get(0).getDeletedBy()).isEqualTo(question.getWriter());
    }

    @Test
    @DisplayName("질문과 답변이 있는 경우 삭제 이력을 생성한다")
    void saveFrom_questionWithAnswers() {
        // given
        Answer answer = new Answer(11L, NsUserTest.JAVAJIGI, question, "Answers Contents1");
        question.addAnswer(answer);

        // when
        deleteHistoryService.saveFrom(question);

        // then
        ArgumentCaptor<List<DeleteHistory>> captor = ArgumentCaptor.forClass(List.class);
        verify(deleteHistoryRepository).saveAll(captor.capture());
        
        List<DeleteHistory> deleteHistories = captor.getValue();
        assertThat(deleteHistories).hasSize(2);
        
        // 질문 삭제 이력 검증
        assertThat(deleteHistories.get(0).getContentType()).isEqualTo(ContentType.QUESTION);
        assertThat(deleteHistories.get(0).getContentId()).isEqualTo(question.getId());
        assertThat(deleteHistories.get(0).getDeletedBy()).isEqualTo(question.getWriter());
        
        // 답변 삭제 이력 검증
        assertThat(deleteHistories.get(1).getContentType()).isEqualTo(ContentType.ANSWER);
        assertThat(deleteHistories.get(1).getContentId()).isEqualTo(answer.getId());
        assertThat(deleteHistories.get(1).getDeletedBy()).isEqualTo(answer.getWriter());
    }
} 