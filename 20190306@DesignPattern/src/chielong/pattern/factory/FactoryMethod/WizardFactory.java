package chielong.pattern.factory.FactoryMethod;

import chielong.pattern.factory.ICharacter;
import chielong.pattern.factory.Wizard;

public class WizardFactory implements ICharacterFactory{
    @Override
    public ICharacter createCharacter() {
        return new Wizard();
    }
}
