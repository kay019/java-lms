package nextstep.qna.domain;

import java.util.List;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsUser;

public class Answer {
    private Long id;

    private Question question;

    private ContentInfo contentInfo;

    private boolean deleted = false;

    private DateInfo dateInfo;

    public Answer() {
    }

    public Answer(NsUser writer, Question question, String contents) {
        this(null, question, new ContentInfo(writer, contents));
    }

    public Answer(Long id, Question question, ContentInfo contentInfo) {
        this.id = id;

        contentInfo.validWriter();

        if(question == null) {
            throw new NotFoundException();
        }

        this.question = question;
        this.contentInfo = contentInfo;
    }

    public Long getId() {
        return id;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isOwner(NsUser writer) {
        return contentInfo.isOwner(writer);
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer [id=" + getId() + ", contentInfo=" + contentInfo + "]";
    }

    public void addDeleteHistory(List<DeleteHistory> deleteHistories) {
        contentInfo.addDeleteHistory(ContentType.ANSWER, deleteHistories, id);
    }
}
