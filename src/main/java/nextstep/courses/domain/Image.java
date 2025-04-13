package nextstep.courses.domain;

import java.util.Arrays;
import java.util.List;

public class Image {
    private Dimension dimension;
    private String type;
    private long size;
    
    public Image() {
    }
    
    public Image(final int width, final int height, final String type, final long size) {
        validateImage(width, height, type, size);
        this.dimension = new Dimension(width, height);
        this.type = type;
        this.size = size;
    }
    
    private void validateImage(final int width, final int height, final String type, final long size) {
        if (size > 1024 * 1024) {
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다");
        }

        if (!isValidImageType(type)) {
            throw new IllegalArgumentException("이미지 타입은 gif, jpg, jpeg, png, svg 중 하나여야 합니다");
        }

        if (width < 300 || height < 200) {
            throw new IllegalArgumentException("이미지 크기는 최소 300x200 픽셀 이상이어야 합니다");
        }

        if (width * 2 != height * 3) {
            throw new IllegalArgumentException("이미지의 가로와 세로 비율은 3:2여야 합니다");
        }
    }

    private boolean isValidImageType(final String type) {
        if (type == null) {
            return false;
        }

        return List.of("gif", "jpg", "jpeg", "png", "svg").contains(type);
    }
    
    public static class Dimension {
        private final int width;
        private final int height;
        
        public Dimension(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
} 
