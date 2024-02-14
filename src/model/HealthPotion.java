package model;

public class HealthPotion extends AbstractConsumable {
    public HealthPotion(final String theName, final int theQuantity) {
        super(theName, theQuantity);
    }

    @Override
    public void triggerEffect() {
        // Change user health here.
    }
}
