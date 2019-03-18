package chielong.pattern.decorator.battercake.v2;

public class BattercakeDecorator extends Battercake {

    private Battercake batterccake;

    public BattercakeDecorator(Battercake batterccake) {
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
