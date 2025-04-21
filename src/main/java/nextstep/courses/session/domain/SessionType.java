package nextstep.courses.session.domain;

public enum SessionType {
    FREE{
        @Override
        public EnrollStrategy getStrategy() {
            return new FreeEnrollStrategy();
        }
    },
    PAID {
        @Override
        public EnrollStrategy getStrategy() {
            return new PaidEnrollStrategy();
        }
    };

    public abstract EnrollStrategy getStrategy();
}
