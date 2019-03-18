package chielong.pattern.decorator.battercake.v2;

public class BaseBatteracke extends Battercake {
    @Override
    protected String getMsg() {
        return "哒哒哒 , 1个煎饼";
    }

    @Override
    protected int getPrice() {
        return 5;
    }
}
