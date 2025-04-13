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

    public void add(DeleteHistory deleteHistory) {
        deleteHistories.add(deleteHistory);
    }

    public int size(){
        return deleteHistories.size();
    }

    public void add(List<DeleteHistory> delete) {
        deleteHistories.addAll(delete);
    }
}
