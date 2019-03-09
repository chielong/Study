package chielong.pattern.FactoryPattern.FactoryMethod;

import chielong.pattern.FactoryPattern.ICharacter;
import chielong.pattern.FactoryPattern.Swordsman;

public class SwordsmanFactory implements ICharacterFactory {
    @Override
    public ICharacter createCharacter() {
        return new Swordsman();
    }
}
