package nextstep.courses.domain;

/**
 * 강의(Session) 표지
 */
public class Cover {
    // 표지 이미지 URL
    private String imageUrl;
    private String filename;
    private CoverFileSize coverFileSize;
    private CoverSize coverSize;
    private CoverExtension extension;

    public Cover(String imageUrl, String filename, CoverFileSize coverFileSize, CoverSize coverSize, CoverExtension extension) {
        this.imageUrl = imageUrl;
        this.filename = filename;
        this.coverFileSize = coverFileSize;
        this.coverSize = coverSize;
        this.extension = extension;
    }
}
