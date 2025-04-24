package nextstep.courses.domain.image;

import java.util.List;

public class CoverImages {
    private final List<CoverImage> images;

    private final Long sessionId;


    public CoverImages(List<CoverImage> images, Long sessionId) {
        this.images = images;
        this.sessionId = sessionId;
    }
}
