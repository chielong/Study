package chielong.pattern.factory.AbstractFactory;

public class WizardCharacterFactory implements CharacterFactory{
    @Override
    public IClothes createClothes() {
        return null;
    }

    @Override
    public IWeapon createWeapon() {
        return null;
    }
}
