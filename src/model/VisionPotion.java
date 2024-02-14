package model;

public class VisionPotion extends AbstractConsumable {
    public VisionPotion(final String theName, final int theQuantity) {
        super(theName, theQuantity);
    }

    @Override
    public void triggerEffect() {
        // Reveal rooms using new vision here.
    }
}
