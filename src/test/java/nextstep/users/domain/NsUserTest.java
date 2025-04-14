package nextstep.users.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class NsUserTest {

    public static final NsUser JAVAJIGI = new NsUser(1L, "javajigi", "password", "name",
        "javajigi@slipp.net");
    public static final NsUser SANJIGI = new NsUser(2L, "sanjigi", "password", "name",
        "sanjigi@slipp.net");


    @Nested
    @DisplayName("NsUser는 로그인한 사용자와 글(질문, 답변)의 작성자가 동일한지 판단할 수 있다.")
    class isSameUser {

        @Test
        @DisplayName("로그인한 사용자와 글의 작성자가 동일하다면 true를 반환한다.")
        void loginQuestionIsSame() {
            assertThat(JAVAJIGI.equals(JAVAJIGI)).isTrue();
        }

        @Test
        @DisplayName("로그인한 사용자와 글의 작성자가 다르다면 false를 반환한다.")
        void loginQuestionIsNotSame() {
            assertThat(JAVAJIGI.equals(SANJIGI)).isFalse();
        }
    }
}
