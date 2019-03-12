package chielong.pattern.factory.AbstractFactory;

public interface CharacterFactory {
    IClothes createClothes();
    IWeapon createWeapon();
}
