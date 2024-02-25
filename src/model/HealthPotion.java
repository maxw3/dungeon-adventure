package model;

public class HealthPotion extends AbstractConsumable {
    public HealthPotion() {
        super("Health Potion");
    }

    @Override
    public String toString() {
        return "The Health Potion heals for 50% of the model.Hero's maximum Hit Points.";
    }
}
