package nextstep.courses.domain;

import nextstep.courses.CannotCreateSessionException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Session {
    private Long id;
    private Period sessionPeriod;
    private List<Image> coverImages = new ArrayList<>();
    private Registry registry;
    private PositiveNumber price;
    private SessionProgressState sessionState;
    private SessionAccessType sessionAccessType;

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionProgressState sessionState, SessionRecruitmentState sessionRecruitmentState, PayStrategy payStrategy, Image coverImage, Long capacity, SessionAccessType sessionAccessType) {
        this(id, startDate, endDate, new Registry(payStrategy, sessionRecruitmentState, new PositiveNumber(capacity), sessionAccessType), coverImage, 0L, sessionState);
    }

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionProgressState sessionState, SessionRecruitmentState sessionRecruitmentState, PayStrategy payStrategy, Image coverImage, Long capacity, Long price, SessionAccessType sessionAccessType) {
        this(id, startDate, endDate, new Registry(payStrategy, sessionRecruitmentState, new PositiveNumber(capacity), sessionAccessType), coverImage, price, sessionState);
    }

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, Registry registry, Image coverImage, Long price, SessionProgressState sessionState) {
        this(id, startDate, endDate, registry, Arrays.asList(coverImage), price, sessionState);
    }

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, Registry registry, List<Image> coverImages, Long price, SessionProgressState sessionState) {
        this.id = id;
        this.sessionPeriod = new Period(startDate, endDate);
        this.coverImages = coverImages;
        this.registry = registry;
        this.price = new PositiveNumber(price);
        this.sessionState = sessionState;
        this.sessionAccessType = sessionAccessType;
    }

    public void register(NsUser user, PositiveNumber money) {
        registry.register(user, id, money, price);
    }

    public void confirmUser(NsUser user, ApplicationState applicationState) {
        registry.confirmStudent(user, applicationState);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return sessionPeriod.getStartDate();
    }

    public LocalDateTime getEndDate() {
        return sessionPeriod.getEndDate();
    }

    public List<Image> getCoverImages() {
        return coverImages;
    }

    public Registry getRegistry() {
        return registry;
    }

    public Long getPrice() {
        return price.value();
    }

    public SessionProgressState getSessionState() {
        return sessionState;
    }
}
