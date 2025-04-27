package nextstep.session.domain.type;

public class FreeSessionType extends SessionType {
    private static final long FREE_PRICE = 0L;

    public FreeSessionType() {
        super(FREE_PRICE);
    }

    @Override
    protected void validatePayment(Long amount) {
        if (amount != FREE_PRICE) {
            throw new IllegalArgumentException("payment price must be 0");
        }
    }
}
