package nextstep.courses.domain;

public class CoverImageSize {
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final int RATIO_WIDTH = 3;
    private static final int RATIO_HEIGHT = 2;

    private int width;
    private int height;

    public CoverImageSize(int width, int height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException(
                String.format("width는 %s 이상이여야하고, hegith는 %s 이상이여야합니다.", width, height));
        }

        if (width * RATIO_HEIGHT != height * RATIO_WIDTH){
            throw new IllegalArgumentException("width와 height의 비율은 3:2여야합니다.");
        }

        this.width = width;
        this.height = height;
    }
}
