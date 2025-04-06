package nextstep.qna.domain;

import nextstep.qna.exception.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private Long id;
    private String title;
    private String contents;
    private NsUser writer;
    private Answers answers = new Answers();
    private boolean deleted = false;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate;

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void addAnswer(Answer answer) {
        answers = new Answers(answers, answer);
        answer.toQuestion(this);
        this.updatedDate = LocalDateTime.now();
    }

    private boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        validateDeletable(loginUser);
        return delete();
    }

    private void validateDeletable(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
        if (answers.isEmpty()) {
            return;
        }
        if (!answers.areAllAnswersSameWriter(writer)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    private List<DeleteHistory> delete() {
        this.deleted = true;
        return createDeleteHistoryList();
    }

    private List<DeleteHistory> createDeleteHistoryList() {
        List<DeleteHistory> deleteHistoryList = new ArrayList<>();
        deleteHistoryList.add(new DeleteHistory(ContentType.QUESTION, id, writer));
        deleteHistoryList.addAll(answers.delete());
        return deleteHistoryList;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
