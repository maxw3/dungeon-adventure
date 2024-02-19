package model;

public class VisionPotion extends AbstractConsumable {
    public VisionPotion(final int theQuantity) {
        super("Vision Potion", theQuantity);
    }

    @Override
    public void triggerEffect() {
        // Reveal rooms using new vision here.
    }
}
