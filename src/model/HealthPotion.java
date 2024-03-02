package model;

public class HealthPotion extends AbstractConsumable {
    public HealthPotion() {
        super("Health Potion");
    }

    public HealthPotion(final int theQuantity) {
        super("Health Potion", theQuantity);
    }
    @Override
    public void triggerEffect() {
        super.triggerEffect();
        final Hero hero = DungeonLogic.MY_INSTANCE.getHero();
        hero.healOrDamage(hero.myMaxHP / 2);
    }
    @Override
    public String toString() {
        return "The Health Potion heals for 50% of the model.Hero's maximum Hit Points.";
    }
}
