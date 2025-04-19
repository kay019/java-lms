package nextstep.stub;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;

import java.util.Optional;

public class TestUserRepository implements UserRepository {
    private int findByUserIdCalled = 0;
    private final Optional<NsUser> findByUserIdResult;

    public TestUserRepository(Optional<NsUser> findByUserIdResult) {
        this.findByUserIdResult = findByUserIdResult;
    }

    @Override
    public Optional<NsUser> findByUserId(String userId) {
        findByUserIdCalled++;
        return findByUserIdResult;
    }

    public int getFindByUserIdCalled() {
        return findByUserIdCalled;
    }
}
