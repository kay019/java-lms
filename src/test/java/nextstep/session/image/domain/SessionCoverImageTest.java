package nextstep.session.image.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.exception.SessionCoverImageIllegalArgumentException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionCoverImageTest {
    private ImageFileSize createValidFileSize() {
        return new ImageFileSize(1024 * 1024);
    }

    private ImageSize createValidImageSize() {
        return new ImageSize(300, 200);
    }

    private ImageType createValidImageType() {
        return ImageType.JPEG;
    }

    @Test
    @DisplayName("모든 필드가 유효할 때 정상 생성")
    void createWithValidParameters() {
        ImageFileSize fileSize = createValidFileSize();
        ImageType type = createValidImageType();
        ImageSize imageSize = createValidImageSize();

        SessionCoverImage coverImage = new SessionCoverImage(1L, 1L, fileSize, type, imageSize);

        assertThat(coverImage.getId()).isEqualTo(1L);
        assertThat(coverImage.getSessionId()).isEqualTo(1L);
        assertThat(coverImage.getFileSize()).isEqualTo(fileSize);
        assertThat(coverImage.getType()).isEqualTo(type);
        assertThat(coverImage.getImageSize()).isEqualTo(imageSize);
    }

    @Test
    @DisplayName("fileSize가 null일 때 예외 발생")
    void throwExceptionWhenFileSizeIsNull() {
        ImageType type = createValidImageType();
        ImageSize imageSize = createValidImageSize();

        assertThatThrownBy(() -> new SessionCoverImage(1L, 1L, null, type, imageSize))
            .isInstanceOf(SessionCoverImageIllegalArgumentException.class);
    }

    @Test
    @DisplayName("type이 null일 때 예외 발생")
    void throwExceptionWhenTypeIsNull() {
        ImageFileSize fileSize = createValidFileSize();
        ImageSize imageSize = createValidImageSize();

        assertThatThrownBy(() -> new SessionCoverImage(1L, 1L, fileSize, null, imageSize))
            .isInstanceOf(SessionCoverImageIllegalArgumentException.class);
    }

    @Test
    @DisplayName("imageSize가 null일 때 예외 발생")
    void throwExceptionWhenImageSizeIsNull() {
        ImageFileSize fileSize = createValidFileSize();
        ImageType type = createValidImageType();

        assertThatThrownBy(() -> new SessionCoverImage(1L, 1L, fileSize, type, null))
            .isInstanceOf(SessionCoverImageIllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 필드가 null일 때 예외 발생")
    void throwExceptionWhenAllFieldsAreNull() {
        assertThatThrownBy(() -> new SessionCoverImage(1L, 1L, null, null, null))
            .isInstanceOf(SessionCoverImageIllegalArgumentException.class);
    }
}
