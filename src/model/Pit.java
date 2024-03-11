package model;

import enums.ItemType;

import java.io.Serializable;
import java.util.Random;

/**
 * An item that's unobtainable for the hero
 * When encountering this for the first time, it harms the hero
 */
public final class Pit implements Item, Serializable {
    /**
     * random number generator
     */
    private static final Random RANDOM = new Random();
    /**
     * the type of item this is
     */
    private static final ItemType MY_TYPE = ItemType.PIT;
    @Override
    public String getName() {
        return "Pit";
    }

    @Override
    public String getType() {
        return MY_TYPE.name();
    }

    /**
     * hurts the hero
     * @param theCharacter the Hero
     * @return how much damage was dealt?
     */
    public int activate(final AbstractDungeonCharacter theCharacter) {
        final int damage = -(RANDOM.nextInt(20) + 1);
        theCharacter.healOrDamage(damage);
        return damage;
    }
}
