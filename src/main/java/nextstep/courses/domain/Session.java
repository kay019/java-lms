package nextstep.courses.domain;

import nextstep.courses.CannotCreateSessionException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Session {
    private Long id;
    private Period sessionPeriod;
    private Image coverImage;
    private Registry registry;
    private PositiveNumber price;
    private SessionProgressState sessionState;

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionProgressState sessionState, SessionRecruitmentState sessionRecruitmentState, PayStrategy payStrategy, Image coverImage, Long capacity) {
        this(id, startDate, endDate, new Registry(payStrategy, sessionRecruitmentState, new PositiveNumber(capacity)), coverImage, 0L, sessionState);
    }

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionProgressState sessionState, SessionRecruitmentState sessionRecruitmentState, PayStrategy payStrategy, Image coverImage, Long capacity, Long price) {
        this(id, startDate, endDate, new Registry(payStrategy, sessionRecruitmentState, new PositiveNumber(capacity)), coverImage, price, sessionState);
    }

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, Registry registry, Image coverImage, SessionProgressState sessionState) {
        this(id, startDate, endDate, registry, coverImage, 0L, sessionState);
    }

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, Registry registry, Image coverImage, Long price, SessionProgressState sessionState) {
        this.id = id;
        this.sessionPeriod = new Period(startDate, endDate);
        this.coverImage = coverImage;
        this.registry = registry;
        this.price = new PositiveNumber(price);
        this.sessionState = sessionState;
    }

    public void register(NsUser user, PositiveNumber money) {
        registry.register(user, id, money, price);
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

    public Long getImageSize() {
        return coverImage.getSize();
    }

    public Long getImageHeight() {
        return coverImage.getHeight();
    }

    public Long getImageWidth() {
        return coverImage.getWidth();
    }

    public String getImageType() {
        return coverImage.getImageType();
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
