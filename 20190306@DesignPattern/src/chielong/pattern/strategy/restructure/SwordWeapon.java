package chielong.pattern.strategy.restructure;

public class SwordWeapon implements Weaponable {
    @Override
    public void hit() {
        System.out.println("you dead ! this is sword");
    }
}
