package nextstep.courses.domain;

public class Image {
    private String fileName;
    private String contentType;
    private long sizeInBytes;
    private int width;
    private int height;

    public Image(String fileName, String contentType, long sizeInBytes, int width, int height) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.sizeInBytes = sizeInBytes;
        this.width = width;
        this.height = height;
        validate();
    }

    private void validate() {
        if (sizeInBytes > 1024 * 1024) {
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다.");
        }

        if (!contentType.matches("(gif|jpeg|jpg|png|svg)")) {
            throw new IllegalArgumentException("허용되지 않는 이미지 형식입니다.");
        }

        if (width < 300 || height < 200) {
            throw new IllegalArgumentException("이미지 크기가 너무 작습니다.");
        }

        double ratio = (double) width / height;
        if (Math.abs(ratio - 1.5) > 0.01) {
            throw new IllegalArgumentException("이미지 비율은 3:2여야 합니다.");
        }
    }
}
