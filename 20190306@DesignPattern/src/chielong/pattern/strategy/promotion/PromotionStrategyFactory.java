package chielong.pattern.strategy.promotion;

import java.util.HashMap;
import java.util.Map;

public class PromotionStrategyFactory {

    private static final Map<String , PromotionStrategy> PROMOTION_STRATEGY_MAP
            = new HashMap<String , PromotionStrategy>();

    static {
        PROMOTION_STRATEGY_MAP.put(PromotionKey.COUPON , new CouponStrategy());
        PROMOTION_STRATEGY_MAP.put(PromotionKey.CASH_BACK , new CashbackStrategy());
        PROMOTION_STRATEGY_MAP.put(PromotionKey.EMPTY , new EmptyStrategy());
    }

    private PromotionStrategyFactory() {}

    public static PromotionStrategy getPromotionStrategy(String promotionKey) {
        PromotionStrategy promotion = PROMOTION_STRATEGY_MAP.get(promotionKey);
        return null == promotion ? new EmptyStrategy() : promotion;
    }

    private interface PromotionKey {
        String COUPON = "Coupon";
        String EMPTY = "Empty";
        String CASH_BACK = "cashback";
    }
}
