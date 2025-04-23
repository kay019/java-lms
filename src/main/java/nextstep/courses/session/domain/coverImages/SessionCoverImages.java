package nextstep.courses.session.domain.coverImages;

import java.util.List;

public class SessionCoverImages {
    private final List<SessionCoverImage> images;

    public SessionCoverImages(List<SessionCoverImage> images) {
        validate(images);
        this.images = images;
    }

    private void validate(List<SessionCoverImage> images) {
        if (images.isEmpty()) {
            throw new IllegalArgumentException("커버 이미지는 하나 이상이어야 합니다.");
        }
    }
}
