package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class Registration {

    private Long id;
    private Registrant registrant;
    private LocalDateTime registeredAt;
    
    public Registration() {
    }

    public Registration(final CourseSession session, final NsUser user) {
        this.registrant = new Registrant(session, user);
        this.registeredAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    
    public static class Registrant {
        private final CourseSession session;
        private final NsUser user;
        
        public Registrant(CourseSession session, NsUser user) {
            this.session = session;
            this.user = user;
        }
        
        public CourseSession getSession() {
            return session;
        }
        
        public NsUser getUser() {
            return user;
        }
    }
} 
