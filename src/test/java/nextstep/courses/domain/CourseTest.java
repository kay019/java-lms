package nextstep.courses.domain;

import nextstep.courses.course.domain.Course;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    void 선발강의는_선발된_유저만_가능() {
        Course course = Course.selective("선발 코스", 1L);
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> course.canEnrollRequireSelection(false));

        Assertions.assertThat(course.canEnrollRequireSelection(true)).isTrue();
    }
}
