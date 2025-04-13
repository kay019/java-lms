package nextstep.users.domain;

import nextstep.common.domain.Audit;
import nextstep.qna.exception.UnAuthorizedException;

import java.time.LocalDateTime;
import java.util.Objects;

public class NsUser {
    public static final GuestNsUser GUEST_USER = new GuestNsUser();

    private Long id;
    private String userId;
    private String password;
    private UserProfile profile;
    private Audit audit;

    public NsUser() {
    }

    public NsUser(Long id, String userId, String password, String name, String email) {
        this(id, userId, password, name, email, LocalDateTime.now(), null);
    }

    public NsUser(Long id, String userId, String password, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.profile = new UserProfile(name, email);
        this.audit = new Audit(createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return profile.getName();
    }

    public String getEmail() {
        return profile.getEmail();
    }

    public void update(NsUser loginUser, NsUser target) {
        if (!matchUserId(loginUser.getUserId())) {
            throw new UnAuthorizedException();
        }

        if (!matchPassword(target.getPassword())) {
            throw new UnAuthorizedException();
        }

        this.profile = new UserProfile(target.getName(), target.getEmail());
    }

    public boolean matchUser(NsUser target) {
        return matchUserId(target.getUserId());
    }

    private boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchPassword(String targetPassword) {
        return password.equals(targetPassword);
    }

    public boolean equalsNameAndEmail(NsUser target) {
        if (Objects.isNull(target)) {
            return false;
        }

        return profile.getName().equals(target.getName()) &&
                profile.getEmail().equals(target.getEmail());
    }

    public boolean isGuestUser() {
        return false;
    }

    private static class GuestNsUser extends NsUser {
        @Override
        public boolean isGuestUser() {
            return true;
        }
    }

    @Override
    public String toString() {
        return "NsUser{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + profile.getName() + '\'' +
                ", email='" + profile.getEmail() + '\'' +
                ", createdAt=" + audit.getCreatedAt() +
                ", updatedAt=" + audit.getUpdatedAt() +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class UserProfile {
        private final String name;
        private final String email;
        
        public UserProfile(String name, String email) {
            this.name = name;
            this.email = email;
        }
        
        public String getName() {
            return name;
        }
        
        public String getEmail() {
            return email;
        }
    }

    public static class Builder {
        private Long id;
        private String userId;
        private String password;
        private String name;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public NsUser build() {
            if (createdAt == null) {
                createdAt = LocalDateTime.now();
            }
            return new NsUser(id, userId, password, name, email, createdAt, updatedAt);
        }
    }
}
