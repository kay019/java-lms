package nextstep.courses.domain;

public class PremiumPlan {
    private final boolean wooteco;
    private final boolean wootecopro;

    public PremiumPlan(boolean wooteco, boolean wootecopro) {
        this.wooteco = wooteco;
        this.wootecopro = wootecopro;
    }

    public boolean isWooteco() {
        return wooteco;
    }

    public boolean isWootecopro() {
        return wootecopro;
    }
}
