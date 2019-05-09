package chielong.study.standalone.pattern.decorator;

/**
 * Created by chielong on 2019-05-09.
 */
public abstract class Drink {
    protected String desc;

    public String getDesc() {
        return desc;
    }

    public abstract double cost();
}
