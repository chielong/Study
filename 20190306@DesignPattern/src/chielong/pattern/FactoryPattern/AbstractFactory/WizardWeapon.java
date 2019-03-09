package chielong.pattern.FactoryPattern.AbstractFactory;

public class WizardWeapon implements IWeapon {
    @Override
    public void hurt() {
        System.out.println("wand-:hurt +10");
    }
}
