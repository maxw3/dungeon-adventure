package model;

public class VisionPotion extends AbstractConsumable {
    public VisionPotion(final String theName, final int theQuantity) {
        super(theName, theQuantity);
    }

    @Override
    public String toString(){
        return "The Vision Potion extends vision to enable the user to have information on the contents of adjacent rooms.";
    }
}
