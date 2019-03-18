package chielong.pattern.decorator.battercake.v2;

public class EggDecorator extends BattercakeDecorator {

    public EggDecorator(Battercake batterccake) {
        super(batterccake);
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + "1个鸡蛋";
    }

    @Override
    protected int getPrice() {
        return super.getPrice() + 3;
    }
}
