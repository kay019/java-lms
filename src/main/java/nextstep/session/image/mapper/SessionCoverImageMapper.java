package nextstep.session.image.mapper;

import nextstep.session.image.domain.ImageFileSize;
import nextstep.session.image.domain.ImageSize;
import nextstep.session.image.domain.ImageType;
import nextstep.session.image.domain.SessionCoverImage;
import nextstep.session.image.entity.SessionCoverImageEntity;

public class SessionCoverImageMapper {
    public SessionCoverImageEntity toEntity(SessionCoverImage image) {
        return new SessionCoverImageEntity(
            image.getId(),
            image.getSessionId(),
            image.getFileSize().getSize(),
            image.getType().name(),
            image.getImageSize().getWidth(),
            image.getImageSize().getHeight()
        );
    }

    public SessionCoverImage toDomain(SessionCoverImageEntity entity) {
        return new SessionCoverImage(
            entity.getId(),
            entity.getSessionId(),
            new ImageFileSize(entity.getSize()),
            ImageType.valueOf(entity.getType()),
            new ImageSize(entity.getWidth(), entity.getHeight())
        );
    }
}
