package chielong.pattern.FactoryPattern.AbstractFactory;

public class SwordWeapon implements IWeapon {
    @Override
    public void hurt() {
        System.out.println("sword-hurt:hurt +10");
    }
}
