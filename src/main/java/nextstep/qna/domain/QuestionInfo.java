package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

public class QuestionInfo {
    private final Long id;

    private final NsUser writer;

    private final String title;

    private final String contents;

    public QuestionInfo(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public NsUser getWriter() {
        return writer;
    }

    @Override
    public String toString() {
        return "QuestionInfo{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            ", writer=" + writer +
            '}';
    }
}
