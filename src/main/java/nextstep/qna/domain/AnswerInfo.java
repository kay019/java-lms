package nextstep.qna.domain;

import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

public class AnswerInfo {
    private final Long id;

    private final NsUser writer;

    private final String contents;

    public AnswerInfo(Long id, NsUser writer, String contents) {
        this.id = id;

        if (writer == null) {
            throw new UnAuthorizedException();
        }

        this.writer = writer;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public NsUser getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "AnswerInfo{" +
            "id=" + id +
            ", writer=" + writer +
            ", contents='" + contents + '\'' +
            '}';
    }
}
