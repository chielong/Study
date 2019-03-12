package chielong.pattern.factory.AbstractFactory;

public class SwordHelme implements IClothes {
    @Override
    public void protect() {
        System.out.println("helme-protect:hurt -10");
    }
}
