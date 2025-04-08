package nextstep.qna.infrastructure;

import nextstep.qna.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository {
    Optional<Question> findById(Long id);

    void save(final Question question);
}
