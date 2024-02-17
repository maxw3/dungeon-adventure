package model;

public class HealthPotion extends AbstractConsumable {
    public HealthPotion(final String theName, final int theQuantity) {
        super(theName, theQuantity);
    }

    @Override
    public String toString(){
        return "The Health Potion heals for 50% of the model.Hero's maximum Hit Points.";
    }
}
