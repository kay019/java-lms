package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteHistoriesTest {
    DeleteHistories deleteHistories = new DeleteHistories();

    @Test
    void add(){
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now()));
        assertThat(deleteHistories.getDeleteHistories().size()).isEqualTo(1);
    }

}
