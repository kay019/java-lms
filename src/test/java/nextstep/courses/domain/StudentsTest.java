package nextstep.courses.domain;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentsTest {
    @Test
    @DisplayName("수강생을 추가하면 size가 늘어난다.")
    void addTest() {
        Students students = new Students();
        NsUser user1 = NsUserTest.JAVAJIGI;
        NsUser user2 = NsUserTest.SANJIGI;

        students.add(user1);
        students.add(user2);

        assertEquals(2, students.size());
    }

    @Test
    @DisplayName("수강생이 없으면 사이즈는 0이다.")
    void sizeTest_zeroStudent() {
        Students students = new Students();

        assertEquals(0, students.size());
    }

}
