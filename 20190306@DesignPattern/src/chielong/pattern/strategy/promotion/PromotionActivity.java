package chielong.pattern.strategy.promotion;

public class PromotionActivity {
    PromotionStrategy promotion;

    public PromotionActivity(PromotionStrategy promotion) {
        this.promotion = promotion;
    }

    public void execute() {
        promotion.doPromotion();
    }
}
