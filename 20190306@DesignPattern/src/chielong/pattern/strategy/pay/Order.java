package chielong.pattern.strategy.pay;

public class Order {
    private String uid;
    private String orderId;
    private double amount;

    public Order(String uid, String orderId, double amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    public MsgResult pay(String payMode) {
        return null;
    }

}
