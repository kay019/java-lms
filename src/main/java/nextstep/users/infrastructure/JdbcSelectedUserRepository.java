package nextstep.users.infrastructure;

import nextstep.users.domain.SelectedUser;
import nextstep.users.domain.SelectedUserRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("selectedUserRepository")
public class JdbcSelectedUserRepository implements SelectedUserRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSelectedUserRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean existsByCourseIdAndUserId(Long courseId, Long userId) {
        String sql = "SELECT COUNT(*) FROM selected_user WHERE course_id = ? AND user_id = ? AND selected = true";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, courseId, userId);
        return count != null && count > 0;
    }
}
