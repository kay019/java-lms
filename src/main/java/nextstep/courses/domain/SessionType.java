package nextstep.courses.domain;

public class SessionType {
    private final PricingType pricingType;
    private final ParticipantType participantType;

    public SessionType(PricingType pricingType, ParticipantType participantType) {
        this.pricingType = pricingType;
        this.participantType = participantType;
    }

    public PricingType getPricingType() {
        return pricingType;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }
}
