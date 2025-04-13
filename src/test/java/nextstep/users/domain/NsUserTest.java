package nextstep.users.domain;

import nextstep.qna.exception.UnAuthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NsUserTest {
    public static final NsUser JAVAJIGI = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("name")
            .email("javajigi@slipp.net")
            .build();
    public static final NsUser SANJIGI = NsUser.builder()
            .id(2L)
            .userId("sanjigi")
            .password("password")
            .name("name")
            .email("sanjigi@slipp.net")
            .build();

    @Test
    @DisplayName("사용자 기본 생성자 테스트")
    public void createUser() {
        NsUser user = NsUser.builder()
            .id(3L)
            .userId("user3")
            .password("password3")
            .name("User Three")
            .email("user3@example.com")
            .build();
        
        assertThat(user.getId()).isEqualTo(3L);
        assertThat(user.getUserId()).isEqualTo("user3");
        assertThat(user.getPassword()).isEqualTo("password3");
        assertThat(user.getName()).isEqualTo("User Three");
        assertThat(user.getEmail()).isEqualTo("user3@example.com");
    }
    
    @Test
    @DisplayName("사용자 빌더 패턴 테스트")
    public void buildUser() {
        NsUser user = NsUser.builder()
            .id(3L)
            .userId("testuser")
            .password("testpass")
            .name("Test User")
            .email("test@example.com")
            .build();
            
        assertThat(user.getUserId()).isEqualTo("testuser");
        assertThat(user.getPassword()).isEqualTo("testpass");
        assertThat(user.getName()).isEqualTo("Test User");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
    }
    
    @Test
    @DisplayName("사용자 정보 업데이트 성공 테스트")
    public void updateUserSuccess() {
        NsUser original = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Original Name")
            .email("original@example.com")
            .build();
            
        NsUser loginUser = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Updated Name")
            .email("updated@example.com")
            .build();
            
        NsUser updatedInfo = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Updated Name")
            .email("updated@example.com")
            .build();
        
        original.update(loginUser, updatedInfo);
        
        assertThat(original.getName()).isEqualTo("Updated Name");
        assertThat(original.getEmail()).isEqualTo("updated@example.com");
    }
    
    @Test
    @DisplayName("다른 사용자가 정보 업데이트 시도 시 예외 발생")
    public void updateUserFailureDifferentUser() {
        NsUser original = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Original Name")
            .email("original@example.com")
            .build();
            
        NsUser differentUser = NsUser.builder()
            .id(2L)
            .userId("sanjigi")
            .password("password")
            .name("Different User")
            .email("different@example.com")
            .build();
            
        NsUser updatedInfo = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Updated Name")
            .email("updated@example.com")
            .build();
        
        assertThatThrownBy(() -> original.update(differentUser, updatedInfo))
                .isInstanceOf(UnAuthorizedException.class);
    }
    
    @Test
    @DisplayName("잘못된 비밀번호로 정보 업데이트 시도 시 예외 발생")
    public void updateUserFailureWrongPassword() {
        NsUser original = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Original Name")
            .email("original@example.com")
            .build();
            
        NsUser loginUser = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Login User")
            .email("login@example.com")
            .build();
            
        NsUser updatedInfo = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("wrongpassword")
            .name("Updated Name")
            .email("updated@example.com")
            .build();
        
        assertThatThrownBy(() -> original.update(loginUser, updatedInfo))
                .isInstanceOf(UnAuthorizedException.class);
    }
    
    @Test
    @DisplayName("사용자 일치 확인 테스트")
    public void matchUser() {
        NsUser user = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Name")
            .email("email@example.com")
            .build();
            
        NsUser sameIdUser = NsUser.builder()
            .id(2L)
            .userId("javajigi")
            .password("different")
            .name("Different")
            .email("diff@example.com")
            .build();
            
        NsUser differentIdUser = NsUser.builder()
            .id(3L)
            .userId("different")
            .password("password")
            .name("Name")
            .email("email@example.com")
            .build();
        
        assertThat(user.matchUser(sameIdUser)).isTrue();
        assertThat(user.matchUser(differentIdUser)).isFalse();
    }
    
    @Test
    @DisplayName("비밀번호 일치 확인 테스트")
    public void matchPassword() {
        NsUser user = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Name")
            .email("email@example.com")
            .build();
        
        assertThat(user.matchPassword("password")).isTrue();
        assertThat(user.matchPassword("wrongpassword")).isFalse();
    }
    
    @Test
    @DisplayName("이름과 이메일 동등성 테스트")
    public void equalsNameAndEmail() {
        NsUser user = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Name")
            .email("email@example.com")
            .build();
            
        NsUser sameNameEmail = NsUser.builder()
            .id(2L)
            .userId("different")
            .password("different")
            .name("Name")
            .email("email@example.com")
            .build();
            
        NsUser differentName = NsUser.builder()
            .id(3L)
            .userId("different")
            .password("different")
            .name("Different")
            .email("email@example.com")
            .build();
            
        NsUser differentEmail = NsUser.builder()
            .id(4L)
            .userId("different")
            .password("different")
            .name("Name")
            .email("different@example.com")
            .build();
            
        NsUser nullUser = null;
        
        assertThat(user.equalsNameAndEmail(sameNameEmail)).isTrue();
        assertThat(user.equalsNameAndEmail(differentName)).isFalse();
        assertThat(user.equalsNameAndEmail(differentEmail)).isFalse();
        assertThat(user.equalsNameAndEmail(nullUser)).isFalse();
    }
    
    @Test
    @DisplayName("게스트 사용자 확인 테스트")
    public void isGuestUser() {
        NsUser regularUser = NsUser.builder()
            .id(1L)
            .userId("javajigi")
            .password("password")
            .name("Name")
            .email("email@example.com")
            .build();
            
        NsUser guestUser = NsUser.GUEST_USER;
        
        assertThat(regularUser.isGuestUser()).isFalse();
        assertThat(guestUser.isGuestUser()).isTrue();
    }
}
