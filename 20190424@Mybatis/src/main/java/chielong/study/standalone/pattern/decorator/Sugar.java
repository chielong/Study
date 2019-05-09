package chielong.study.standalone.pattern.decorator;

/**
 * Created by chielong on 2019-05-09.
 */
public class Sugar extends Condiment {
    private Drink drink;

    public Sugar(Drink drink) {
        this.drink = drink;
    }

    @Override
    public String getDesc() {
        return drink.getDesc() + " +糖";
    }

    @Override
    public double cost() {
        return 1 + drink.cost();
    }
}
