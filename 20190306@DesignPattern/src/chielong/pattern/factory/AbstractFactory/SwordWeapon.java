package chielong.pattern.factory.AbstractFactory;

public class SwordWeapon implements IWeapon {
    @Override
    public void hurt() {
        System.out.println("sword-hurt:hurt +10");
    }
}
