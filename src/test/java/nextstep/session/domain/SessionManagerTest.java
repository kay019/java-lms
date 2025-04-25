package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nextstep.session.exception.NoAuthorityException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SessionManagerTest {
    @Mock
    private Session mockSession;

    @Test
    @DisplayName("기본 생성자는 MANAGER 권한으로 초기화된다")
    void defaultConstructor_SetsManagerAuthority() {
        SessionManager manager = new SessionManager(mockSession);
        assertThat(manager).extracting("authority").isEqualTo(ManagerAuthority.MANAGER);
    }

    @Test
    @DisplayName("MANAGER 권한으로 학생 선택 시 세션 메서드 호출")
    void selectStudent_WithManagerAuthority_CallsSessionMethod() {
        SessionManager manager = new SessionManager(mockSession, ManagerAuthority.MANAGER);
        manager.selectStudent(100L);

        verify(mockSession).selectStudent(100L);
    }

    @Test
    @DisplayName("MANAGER 권한 없는 경우 학생 선택 시 예외 발생")
    void selectStudent_WithoutManagerAuthority_ThrowsException() {
        SessionManager manager = new SessionManager(mockSession, ManagerAuthority.ASSISTANT);

        assertThatThrownBy(() -> manager.selectStudent(100L))
            .isInstanceOf(NoAuthorityException.class);
    }
}
