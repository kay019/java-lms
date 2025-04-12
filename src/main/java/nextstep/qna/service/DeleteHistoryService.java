package nextstep.qna.service;


import javax.annotation.Resource;
import nextstep.qna.domain.DeleteHistoryRepository;
import nextstep.qna.domain.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("deleteHistoryService")
public class DeleteHistoryService {

    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFrom(Question question) {
        deleteHistoryRepository.saveAll(question.createDeleteHistories().getHistories());
    }
}
