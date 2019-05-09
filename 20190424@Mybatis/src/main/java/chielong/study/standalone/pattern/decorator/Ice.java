package chielong.study.standalone.pattern.decorator;

/**
 * Created by chielong on 2019-05-09.
 */
public class Ice extends Condiment {
    private Drink drink;

    public Ice(Drink drink) {
        this.drink = drink;
    }

    @Override
    public String getDesc() {
        return drink.getDesc() + " +冰";
    }

    @Override
    public double cost() {
        return 0.5 + drink.cost();
    }
}
