package nextstep.users.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NsUserTest {

    private final NsUser user = new NsUser(1L, "userId", "password", "name", "email");
    private final NsUser guestUser = NsUser.GUEST_USER;

    @Test
    public void 일반_사용자_확인() {
        assertThat(user.isGuestUser()).isFalse();
    }

    @Test
    public void 게스트_사용자_확인() {
        assertThat(guestUser.isGuestUser()).isTrue();
    }

    @Test
    public void NsUser_생성_및_확인() {
        NsUser newUser = new NsUser(2L, "newUserId", "newPassword", "newName", "newEmail");

        assertThat(newUser.toString()).contains("newUserId", "newName", "newEmail");
        assertThat(newUser.isGuestUser()).isFalse();
    }
}
