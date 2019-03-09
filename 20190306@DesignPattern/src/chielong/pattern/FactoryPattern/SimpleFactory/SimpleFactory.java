package chielong.pattern.FactoryPattern.SimpleFactory;

import chielong.pattern.FactoryPattern.ICharacter;
import chielong.pattern.FactoryPattern.Swordsman;

public class SimpleFactory {
    public ICharacter newCharacter() {
        return new Swordsman();
    }
}
