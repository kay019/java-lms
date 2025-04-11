package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeleteHistories {

    private List<DeleteHistory> deleteHistories = new ArrayList<>();

    public List<DeleteHistory> getDeleteHistories() {
        return deleteHistories;
    }

    public void add(ContentType contentType, Long id, NsUser writer) {
        deleteHistories.add(new DeleteHistory(contentType, id, writer, LocalDateTime.now()));
    }
}
