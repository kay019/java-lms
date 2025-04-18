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

  public DeleteHistory addDeleteHistory(ContentType contentType, Long id) {
    return new DeleteHistory(contentType, id, writer, LocalDateTime.now());
  }

  public void validWriter() {
    if (writer == null) {
      throw new UnAuthorizedException();
    }
  }
}
