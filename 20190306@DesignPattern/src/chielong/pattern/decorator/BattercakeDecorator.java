package chielong.pattern.decorator;

public class BattercakeDecorator extends Batterccake{

    private Batterccake batterccake;

    public BattercakeDecorator(Batterccake batterccake) {
        this.batterccake = batterccake;
    }

    @Override
    protected String getMsg() {
        return this.batterccake.getMsg();
    }

    @Override
    protected int getPrice() {
        return this.batterccake.getPrice();
    }
}
