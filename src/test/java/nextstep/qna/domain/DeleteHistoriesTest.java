package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteHistoriesTest {
    DeleteHistories deleteHistories = new DeleteHistories();

    @Test
    void add(){
        deleteHistories.add(ContentType.QUESTION, 1L, NsUserTest.JAVAJIGI);
        assertThat(deleteHistories.getDeleteHistories().size()).isEqualTo(1);
    }

}
