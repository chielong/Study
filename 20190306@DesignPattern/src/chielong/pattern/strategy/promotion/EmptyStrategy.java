package chielong.pattern.strategy.promotion;

public class EmptyStrategy implements PromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("不优惠");
    }
}
