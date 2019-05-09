package chielong.study.standalone.pattern.decorator;

/**
 * Created by chielong on 2019-05-09.
 */
public class MilkTea extends Drink {
    public MilkTea() {
        desc = "奶茶";
    }

    @Override
    public double cost() {
        return 22;
    }
}
