package nextstep.courses.record;

import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.CoverImageType;

public class CoverImageRecord {
    private Long id;
    private long size;
    private CoverImageType type;
    private int width;
    private int height;

    public void setId(Long id) {
        this.id = id;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setType(CoverImageType type) {
        this.type = type;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public long getSize() {
        return size;
    }

    public CoverImageType getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public CoverImage toCoverImage() {
        return new CoverImage(getId(), getType(), getSize(), getWidth(), getHeight());
    }

    public static CoverImageRecord from(CoverImage coverImage) {
        CoverImageRecord record = new CoverImageRecord();
        record.setId(coverImage.getId());
        record.setSize(coverImage.getSize());
        record.setType(coverImage.getType());
        record.setWidth(coverImage.getWidth());
        record.setHeight(coverImage.getHeight());
        return record;
    }
}
