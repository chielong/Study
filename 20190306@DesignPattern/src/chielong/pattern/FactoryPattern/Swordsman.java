package chielong.pattern.FactoryPattern;

public class Swordsman implements ICharacter {
    @Override
    public void attack() {
        System.out.println("swordsman attack! you lose 100 hp!");
    }
}
