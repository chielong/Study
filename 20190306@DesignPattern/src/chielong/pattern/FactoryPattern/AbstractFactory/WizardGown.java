package chielong.pattern.FactoryPattern.AbstractFactory;

public class WizardGown implements IClothes {
    @Override
    public void protect() {
        System.out.println("Gown-:protect -10");
    }
}
