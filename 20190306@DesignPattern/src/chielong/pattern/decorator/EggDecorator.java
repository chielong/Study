package chielong.pattern.decorator;

public class EggDecorator extends BattercakeDecorator {

    public EggDecorator(Batterccake batterccake) {
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
