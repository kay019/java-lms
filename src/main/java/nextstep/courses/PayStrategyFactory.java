package nextstep.courses;

import nextstep.courses.domain.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class PayStrategyFactory {
    private final Map<String, PayStrategy> strategies = new HashMap<>();

    @Autowired
    public PayStrategyFactory(List<PayStrategy> strategyList) {
        for (PayStrategy strategy : strategyList) {
            strategies.put(strategy.getType(), strategy);
        }
    }

    public PayStrategy getStrategy(String type) {
        PayStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException(type + "은 존재하지 않는 강의 유형입니다.");
        }
        return strategy;
    }
}
