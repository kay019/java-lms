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

    public SessionCoverImage mainImage() {
        // DB 데이터가 존재하기 때문에 이미지가 여러장으로 추가 될 경우 가장 처음 것을 메인이미지로
        return images.get(0);
    }

}
