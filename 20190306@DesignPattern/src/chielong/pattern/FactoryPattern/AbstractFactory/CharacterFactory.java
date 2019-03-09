package chielong.pattern.FactoryPattern.AbstractFactory;

public interface CharacterFactory {
    IClothes createClothes();
    IWeapon createWeapon();
}
