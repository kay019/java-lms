package nextstep.qna.service;

import nextstep.qna.exception.CannotDeleteException;
import nextstep.qna.exception.NotFoundException;
import nextstep.qna.domain.*;
import nextstep.qna.infrastructure.AnswerRepository;
import nextstep.qna.infrastructure.QuestionRepository;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QnAService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final DeleteHistoryService deleteHistoryService;

    public QnAService(
            QuestionRepository questionRepository, final AnswerRepository answerRepository, final DeleteHistoryService deleteHistoryService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.deleteHistoryService = deleteHistoryService;
    }

    @Transactional
    public void deleteQuestion(final NsUser loginUser, long questionId) throws CannotDeleteException {
        Question question = questionRepository.findById(questionId).orElseThrow(NotFoundException::new);
        List<DeleteHistory> deleteHistories = question.delete(loginUser);

        questionRepository.save(question);
        answerRepository.saveAll(question.getAnswers());
        deleteHistoryService.saveAll(deleteHistories);
    }
}
