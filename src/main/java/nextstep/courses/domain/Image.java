package nextstep.courses.domain;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Image {
    private Dimension dimension;
    private ImageType type;
    private long size;
    
    public enum ImageType {
        GIF, JPG, JPEG, PNG, SVG;
        
        public static ImageType fromString(String type) {
            if (!StringUtils.hasText(type)) {
                throw new IllegalArgumentException("이미지 타입은 null일 수 없습니다");
            }
            
            try {
                return ImageType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(String.format("이미지 타입은 %s 중 하나여야 합니다", getValidTypesString()));
            }
        }
        
        public static String getValidTypesString() {
            return Arrays.stream(ImageType.values())
                    .map(imageType -> imageType.name().toLowerCase())
                    .collect(Collectors.joining(", "));
        }
    }
    
    public Image() {
    }
    
    public Image(final int width, final int height, final String type, final long size) {
        validateSize(size);
        this.dimension = new Dimension(width, height);
        this.type = ImageType.fromString(type);
        this.size = size;
    }
    
    private void validateSize(final long size) {
        if (size > 1024 * 1024) {
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다");
        }
    }
    
    public static class Dimension {
        private final int width;
        private final int height;
        
        public Dimension(int width, int height) {
            validateDimension(width, height);
            this.width = width;
            this.height = height;
        }
        
        private void validateDimension(int width, int height) {
            if (width < 300 || height < 200) {
                throw new IllegalArgumentException("이미지 크기는 최소 300x200 픽셀 이상이어야 합니다");
            }

            if (width * 2 != height * 3) {
                throw new IllegalArgumentException("이미지의 가로와 세로 비율은 3:2여야 합니다");
            }
        }
    }
} 
