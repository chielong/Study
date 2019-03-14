package chielong.pattern.strategy.pay.paymode;

public class AliPay extends AbstractPay {
    @Override
    public String getName() {
        return "ali-pay";
    }

    @Override
    protected double queryBalance(String uid) {
        return 0;
    }
}
