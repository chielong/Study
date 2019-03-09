package chielong.pattern.FactoryPattern.FactoryMethod;

import chielong.pattern.FactoryPattern.ICharacter;
import chielong.pattern.FactoryPattern.Wizard;

public class WizardFactory implements ICharacterFactory{
    @Override
    public ICharacter createCharacter() {
        return new Wizard();
    }
}
