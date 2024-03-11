package model;

/**
 * A consumable item that restores health to the user
 */
public final class HealthPotion extends AbstractConsumable {
    /**
     * Constructor for Health Potion
     */
    public HealthPotion() {
        super("Health Potion");
    }

    /**
     * Use the Health Potion
     */
    @Override
    public void triggerEffect() {
        super.triggerEffect();
        final Hero hero = DungeonLogic.MY_INSTANCE.getHero();
        hero.healOrDamage(hero.getMaxHP() / 2);
    }
    @Override
    public String toString() {
        return "The Health Potion heals for 50% of the model.Hero's maximum Hit Points.";
    }
}
