package chielong.study.standalone.pattern.decorator;

/**
 * Created by chielong on 2019-05-09.
 */
public class LemonTea extends Drink {
    public LemonTea() {
        desc = "柠檬茶";
    }

    @Override
    public double cost() {
        return 10;
    }
}
