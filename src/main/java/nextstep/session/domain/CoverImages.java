package nextstep.session.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoverImages {

    private final List<CoverImage> coverImages;

    public CoverImages() {
        this(new ArrayList<>());
    }

    public CoverImages(List<CoverImage> coverImages) {
        this.coverImages = coverImages;
    }

    public void add(CoverImage coverImage) {
        coverImages.add(coverImage);
    }

    public List<CoverImage> getCoverImages() {
        return coverImages;
    }

    public void setSessionId(Long sessionId) {
        for (CoverImage coverImage : coverImages) {
            coverImage.setSessionId(sessionId);
        }
    }

    public List<Long> getIds() {
        return getCoverImages()
            .stream()
            .map(CoverImage::getId)
            .collect(Collectors.toList());
    }
}
