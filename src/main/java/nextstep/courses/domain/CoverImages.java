package nextstep.courses.domain;

import java.util.List;

public class CoverImages {
    private final List<CoverImage> values;

    public CoverImages(List<CoverImage> values) {
        this.values = values;
    }

    public List<CoverImage> getValues() {
        return values;
    }
}
