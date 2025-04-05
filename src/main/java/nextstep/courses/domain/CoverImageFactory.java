package nextstep.courses.domain;

public class CoverImageFactory {

    public static CoverImage ofGif(long size, int width, int height) {
        return new CoverImage(CoverImageType.GIF, size, width, height);
    }

}
