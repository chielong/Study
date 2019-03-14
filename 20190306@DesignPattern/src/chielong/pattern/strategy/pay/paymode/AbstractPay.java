package chielong.pattern.strategy.pay.paymode;

import chielong.pattern.strategy.pay.MsgResult;

public abstract class AbstractPay {
    public abstract String getName();

    protected abstract double queryBalance(String uid);

    public MsgResult pay(String uid , double amount) {
        if(queryBalance(uid) < amount) {
            return new MsgResult(500 , "支付失败", "余额不足");
        } else {
            return new MsgResult(200 , "支付成功" , "yes!");
        }
    }

}
