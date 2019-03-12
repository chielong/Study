package chielong.pattern.factory.AbstractFactory;

public class WizardGown implements IClothes {
    @Override
    public void protect() {
        System.out.println("Gown-:protect -10");
    }
}
