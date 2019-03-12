package chielong.pattern.proxy.restructure;

public class Account {
    private long cardNumber;

    public Account(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Account clone() {
        return new Account(cardNumber);
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "cardNumber=" + cardNumber +
                '}';
    }
}
