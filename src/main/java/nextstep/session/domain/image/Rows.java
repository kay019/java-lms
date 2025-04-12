package nextstep.session.domain.image;

import java.util.ArrayList;
import java.util.List;

public class Rows {

    private final List<Row> value;

    public Rows(List<Row> rows) {
        if (rows == null || rows.isEmpty()) {
            this.value = new ArrayList<>();
            return;
        }

        int rowSize = rows.get(0).size();

        if (!rows.stream().allMatch(row -> row.size() == rowSize)) {
            throw new IllegalArgumentException("row의 크기들은 동일해야 합니다.");
        }

        this.value = rows;
    }

    public int size() {
        return value.size();
    }

    public int rowSize() {
        if (size() == 0) {
            return 0;
        }
        return value.get(0).size();
    }

    public int byteSize() {
        return value.stream()
            .mapToInt(Row::byteSize)
            .sum();
    }
}
