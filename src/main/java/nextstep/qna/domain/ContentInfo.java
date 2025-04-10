package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.List;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

public class ContentInfo {

  private NsUser writer;

  private String contents;

  public ContentInfo(NsUser writer, String contents) {
    this.writer = writer;
    this.contents = contents;
  }

  public boolean isOwner(NsUser loginUser) {
    return writer.equals(loginUser);
  }

  public void addDeleteHistory(ContentType contentType, List<DeleteHistory> deleteHistories,
      Long id) {
    deleteHistories.add(new DeleteHistory(contentType, id, writer, LocalDateTime
        .now()));
  }

  public void validWriter() {
    if (writer == null) {
      throw new UnAuthorizedException();
    }
  }
}
