package nextstep.courses;

import java.time.LocalDateTime;

public class SessionDto {
    private Long id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private long imageSize;
    private String imageType;
    private long imageWidth;
    private long imageHeight;
    private long price;

    public SessionDto(Long id, LocalDateTime startAt, LocalDateTime endAt, long imageSize, String imageType, long imageWidth, long imageHeight, long price) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.imageSize = imageSize;
        this.imageType = imageType;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public long getImageSize() {
        return imageSize;
    }

    public String getImageType() {
        return imageType;
    }

    public long getImageWidth() {
        return imageWidth;
    }

    public long getImageHeight() {
        return imageHeight;
    }

    public long getPrice() {
        return price;
    }
}
