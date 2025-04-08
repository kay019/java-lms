package nextstep.courses.domain;

import nextstep.courses.record.CoverImageRecord;

public interface CoverImageRepository {
    int save(CoverImageRecord coverImage);
    CoverImageRecord findById(long id);
}
