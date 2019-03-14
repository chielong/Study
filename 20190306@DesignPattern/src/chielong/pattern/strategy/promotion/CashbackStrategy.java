package chielong.pattern.strategy.promotion;

public class CashbackStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("返现 -1000");
    }
}
