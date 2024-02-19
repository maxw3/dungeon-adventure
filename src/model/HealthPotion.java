package model;

public class HealthPotion extends AbstractConsumable {
    public HealthPotion(final int theQuantity) {
        super("Health Potion", theQuantity);
    }

    @Override
    public String toString(){
        return "The Health Potion heals for 50% of the model.Hero's maximum Hit Points.";
    }
}
