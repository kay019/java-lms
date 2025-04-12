package nextstep.session.domain.image;

import java.util.List;

public class Row {
    private final List<Pixel> value;

    public Row(List<Pixel> pixels) {
        this.value = pixels;
    }

    public int size() {
        return value.size();
    }

    public int byteSize() {
        return value.stream()
            .mapToInt(Pixel::byteSize)
            .sum();
    }
}
