package nextstep.courses.session.domain;

public enum SessionType {
    FREE{
        private final EnrollStrategy strategy = new FreeEnrollStrategy();
        @Override
        public EnrollStrategy getStrategy() {
            return strategy;
        }
    },
    PAID {
        private final EnrollStrategy strategy = new PaidEnrollStrategy();
        @Override
        public EnrollStrategy getStrategy() {
            return strategy;
        }
    };

    public abstract EnrollStrategy getStrategy();
}
