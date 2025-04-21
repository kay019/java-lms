package nextstep.session.image.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.session.image.domain.ImageFileSize;
import nextstep.session.image.domain.ImageSize;
import nextstep.session.image.domain.ImageType;
import nextstep.session.image.domain.SessionCoverImage;
import nextstep.session.image.entity.SessionCoverImageEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionCoverImageMapperTest {
    private final SessionCoverImageMapper mapper = new SessionCoverImageMapper();

    @Test
    @DisplayName("toEntity: SessionCoverImage → SessionCoverImageEntity 정확한 변환")
    void toEntity_mappingTest() {
        SessionCoverImage image = new SessionCoverImage(
            1L,
            100L,
            new ImageFileSize(1024),
            ImageType.JPEG,
            new ImageSize(600, 400)
        );

        SessionCoverImageEntity entity = mapper.toEntity(image);

        assertThat(entity)
            .extracting(
                SessionCoverImageEntity::getId,
                SessionCoverImageEntity::getSessionId,
                SessionCoverImageEntity::getSize,
                SessionCoverImageEntity::getType,
                SessionCoverImageEntity::getWidth,
                SessionCoverImageEntity::getHeight
            )
            .containsExactly(
                1L,
                100L,
                1024,
                "JPEG",
                600,
                400
            );
    }

    @Test
    @DisplayName("toDomain: SessionCoverImageEntity → SessionCoverImage 정확한 변환")
    void toDomain_mappingTest() {
        SessionCoverImageEntity entity = new SessionCoverImageEntity(
            2L,
            200L,
            2048,
            "PNG",
            600,
            400
        );

        SessionCoverImage image = mapper.toDomain(entity);

        assertThat(image)
            .extracting(
                SessionCoverImage::getId,
                SessionCoverImage::getSessionId,
                img -> img.getFileSize().getSize(),
                SessionCoverImage::getType,
                img -> img.getImageSize().getWidth(),
                img -> img.getImageSize().getHeight()
            )
            .containsExactly(
                2L,
                200L,
                2048,
                ImageType.PNG,
                600,
                400
            );
    }

    @Test
    @DisplayName("toDomain: 유효하지 않은 type 문자열 시 IllegalArgumentException 발생")
    void toDomain_invalidType() {
        SessionCoverImageEntity entity = new SessionCoverImageEntity(
            3L,
            300L,
            512,
            "INVALID_TYPE",
            640,
            480
        );

        assertThatThrownBy(() -> mapper.toDomain(entity))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
