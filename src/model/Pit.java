package model;

import enums.ItemType;

import java.util.Random;

public class Pit implements Item {
    private static final Random RANDOM = new Random();
    private static final ItemType MY_TYPE = ItemType.PIT;
    @Override
    public String getName() {
        return "Pit";
    }

    @Override
    public String getType() {
        return MY_TYPE.name();
    }

    public void activate(final DungeonCharacter theCharacter) {
        theCharacter.healOrDamage(-(RANDOM.nextInt(20) + 1));
    }
}
