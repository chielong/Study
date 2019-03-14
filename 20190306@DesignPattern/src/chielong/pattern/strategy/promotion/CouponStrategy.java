package chielong.pattern.strategy.promotion;

public class CouponStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("优惠券 -1000");
    }
}
