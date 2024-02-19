package model;

public class HealthPotion extends AbstractConsumable {
    public HealthPotion(final int theQuantity) {
        super("Health Potion", theQuantity);
    }

    @Override
    public void triggerEffect() {
        // Change user health here.
    }
}
