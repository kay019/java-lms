package nextstep.qna.infrastructure;

import nextstep.qna.domain.Answer;

import java.util.List;

public interface AnswerRepository {
    List<Answer> findByQuestion(Long questionId);

    void saveAll(final List<Answer> answers);
}
