package chielong.pattern.factory.SimpleFactory;

import chielong.pattern.factory.ICharacter;
import chielong.pattern.factory.Swordsman;

public class SimpleFactory {
    public ICharacter newCharacter() {
        return new Swordsman();
    }
}
