package nextstep.session.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.session.domain.Student;
import nextstep.session.entity.StudentEntity;

import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;

class StudentMapperTest {
    private final StudentMapper studentMapper = new StudentMapper();

    @Test
    @DisplayName("Student 객체를 StudentEntity로 정확히 변환해야 함")
    void student_toEntity_mapping() {
        Student student = new Student(
            1L,
            JAVAJIGI.getId(),
            200L,
            JAVAJIGI.getName()
        );

        StudentEntity entity = studentMapper.toEntity(student);

        assertThat(entity)
            .extracting(
                StudentEntity::getId,
                StudentEntity::getUserId,
                StudentEntity::getSessionId,
                StudentEntity::getName,
                StudentEntity::getStatus
            )
            .containsExactly(
                1L,
                1L,
                200L,
                "name",
                "PENDING"
            );
    }
}
