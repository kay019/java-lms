package nextstep.courses.repository;

import nextstep.courses.entity.ImageEntity;

public interface ImageRepository {
  long save(ImageEntity session);
  ImageEntity findById(Long id);
}
