package nextstep.session.mapper;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.session.domain.FreeSession;
import nextstep.session.domain.PaidSession;
import nextstep.session.domain.SessionDate;
import nextstep.session.domain.SessionStatus;
import nextstep.session.entity.SessionEntity;

import static nextstep.session.domain.SessionStatus.ENROLLING;
import static org.assertj.core.api.Assertions.assertThat;

class SessionMapperTest {
    private final SessionMapper sessionMapper = new SessionMapper();
    private SessionStatus status;
    private SessionDate sessionDate;

    @BeforeEach
    void setUp() {
        status = ENROLLING;
        sessionDate = new SessionDate(LocalDate.of(2025, 4, 10), LocalDate.of(2025, 4, 20));
    }

    @Test
    @DisplayName("PaidSession을 SessionEntity로 정확히 변환해야 함")
    void paidSession_toEntity_mapping() {
        PaidSession paidSession = new PaidSession.Builder()
            .id(1L)
            .courseId(1L)
            .status(ENROLLING)
            .sessionDate(sessionDate)
            .maxCapacity(50)
            .fee(30000)
            .students(List.of())
            .build();

        SessionEntity entity = sessionMapper.toEntity(paidSession);

        assertThat(entity)
            .extracting(
                SessionEntity::getId,
                SessionEntity::getCourseId,
                SessionEntity::getStatus,
                SessionEntity::getFee,
                SessionEntity::getCapacity,
                SessionEntity::getStartDate,
                SessionEntity::getEndDate,
                SessionEntity::getStudentEntities
            )
            .containsExactly(
                1L,
                1L,
                ENROLLING.name(),
                30000,
                50,
                sessionDate.getStartDate(),
                sessionDate.getEndDate(),
                List.of()
            );
    }

    @Test
    @DisplayName("FreeSession을 SessionEntity로 정확히 변환해야 함")
    void freeSession_toEntity_mapping() {
        FreeSession session = new FreeSession.Builder()
            .id(1L)
            .courseId(1L)
            .status(status)
            .sessionDate(sessionDate)
            .students(List.of())
            .build();

        SessionEntity entity = sessionMapper.toEntity(session);

        assertThat(entity)
            .extracting(
                SessionEntity::getId,
                SessionEntity::getCourseId,
                SessionEntity::getStatus,
                SessionEntity::getFee,
                SessionEntity::getCapacity,
                SessionEntity::getStartDate,
                SessionEntity::getEndDate,
                SessionEntity::getStudentEntities
            )
            .containsExactly(
                1L,
                1L,
                ENROLLING.name(),
                0,
                0,
                sessionDate.getStartDate(),
                sessionDate.getEndDate(),
                List.of()
            );
    }
}
