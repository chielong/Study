package chielong.pattern.FactoryPattern;

public class Wizard implements ICharacter {
    @Override
    public void attack() {
        System.out.println("wizard attack! you lose 90 hp!");
    }
}
