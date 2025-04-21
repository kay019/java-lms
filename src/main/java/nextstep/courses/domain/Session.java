package nextstep.courses.domain;

import nextstep.courses.CannotCreateSessionException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private Long id;
    private Period sessionPeriod;
    private Image coverImage;
    private Registry registry;
    private PositiveNumber price;

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionState sessionState, PayStrategy payStrategy, Image coverImage, Long capacity) {
        this(id, startDate, endDate, new Registry(payStrategy, sessionState, new PositiveNumber(capacity)), coverImage, 0L);
    }

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, SessionState sessionState, PayStrategy payStrategy, Image coverImage, Long capacity, Long price) {
        this(id, startDate, endDate, new Registry(payStrategy, sessionState, new PositiveNumber(capacity)), coverImage, price);
    }

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, Registry registry, Image coverImage) {
        this(id, startDate, endDate, registry, coverImage, 0L);
    }

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, Registry registry, Image coverImage, Long price) {
        this.id = id;
        this.sessionPeriod = new Period(startDate, endDate);
        this.coverImage = coverImage;
        this.registry = registry;
        this.price = new PositiveNumber(price);
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new CannotCreateSessionException("강의의 시작 날짜가 끝나는 날짜보다 뒤입니다.");
        }
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
}
