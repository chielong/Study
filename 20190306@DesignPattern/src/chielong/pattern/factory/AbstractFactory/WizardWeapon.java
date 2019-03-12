package chielong.pattern.factory.AbstractFactory;

public class WizardWeapon implements IWeapon {
    @Override
    public void hurt() {
        System.out.println("wand-:hurt +10");
    }
}
