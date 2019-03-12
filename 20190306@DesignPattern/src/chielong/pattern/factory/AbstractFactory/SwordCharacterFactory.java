package chielong.pattern.factory.AbstractFactory;

public class SwordCharacterFactory implements CharacterFactory{
    @Override
    public IClothes createClothes() {
        return new SwordHelme();
    }

    @Override
    public IWeapon createWeapon() {
        return new SwordWeapon();
    }
}
