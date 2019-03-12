package chielong.pattern.factory.FactoryMethod;

import chielong.pattern.factory.ICharacter;
import chielong.pattern.factory.Swordsman;

public class SwordsmanFactory implements ICharacterFactory {
    @Override
    public ICharacter createCharacter() {
        return new Swordsman();
    }
}
