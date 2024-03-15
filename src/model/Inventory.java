package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The inventory that stores the items that the hero has to use or collect.
 */
public final class Inventory implements Serializable {

    /**
     * String that acts as a line separator for new lines
     */
    private final String myNewLine = System.lineSeparator();
    /**
     * The usable items in the inventory
     */
    private final ArrayList<AbstractEquipment> myConsumableItems;
    /**
     * The pillars in the inventory
     */
    private final ArrayList<Pillar> myPillars = new ArrayList<>(4);

    /**
     * default constructor
     */
    public Inventory() {
        myConsumableItems = new ArrayList<>();
        myPillars.add(null);
        myPillars.add(null);
        myPillars.add(null);
        myPillars.add(null);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("You have ");
        output.append(getHPPotionAmount());
        output.append(" health potions and ");
        output.append(getVisionPotionAmount());
        output.append(" vision potions for a total of ");
        output.append(getSize());
        output.append(" items.");

        output.append(myNewLine);

        output.append("Pillar of Abstraction: ");
        output.append(pillarStatus(myPillars.get(0)));
        output.append(myNewLine);
        output.append("Pillar of Encapsulation: ");
        output.append(pillarStatus(myPillars.get(1)));
        output.append(myNewLine);
        output.append("Pillar Inheritance: ");
        output.append(pillarStatus(myPillars.get(2)));
        output.append(myNewLine);
        output.append("Pillar of Polymorphism: ");
        output.append(pillarStatus(myPillars.get(3)));

        return output.toString();
    }

    /**
     * Does the hero have this pillar?
     * @param thePillar the Pillar
     * @return Does the hero have it?
     */
    private String pillarStatus(final AbstractEquipment thePillar){
        if (thePillar == null){
            return "X";
        } else {
            return "Obtained";
        }
    }

    /**
     * Does the inventory have any of this item?
     * @param theName the item
     * @return Are there any?
     */
    private AbstractEquipment containsItem(final String theName) {
        for (final AbstractEquipment i : myConsumableItems) {
            if (i.getName().equalsIgnoreCase(theName)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Getter for size of inventory
     * @return
     */
    public int getSize() {
        return myConsumableItems.size();
    }

    /**
     * Getter for quantity of a specific item in the inventory
     * @param theItem the item
     * @return the quantity
     */
    public int getCount(final AbstractEquipment theItem) {
        final AbstractEquipment equipment = containsItem(theItem.getName());
        if (equipment != null && equipment.getType().equals("CONSUMABLE")) {
            return ((AbstractConsumable)equipment).getQuantity();
        }
        return 0;
    }

    /**
     * Add an item to the inventory
     * @param theItem
     */
    public void addItem(final AbstractEquipment theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("The Item is null.");
        }
        final AbstractEquipment firstInstance = containsItem(theItem.getName());
        if (firstInstance != null) {
            if (theItem.getType().equals("CONSUMABLE")) {
                final AbstractConsumable oldConsumable = (AbstractConsumable) firstInstance;
                oldConsumable.add();
            } else {
                myConsumableItems.add(theItem);
            }
        } else if (theItem.getType().equals("PILLAR")) {
            switch(theItem.getName()) {
                case "Abstraction":
                    myPillars.add(0, new Pillar("Abstraction"));
                    break;
                case "Encapsulation":
                    myPillars.add(1, new Pillar("Encapsulation"));
                    break;
                case "Inheritance":
                    myPillars.add(2, new Pillar("Inheritance"));
                    break;
                case "Polymorphism":
                    myPillars.add(3, new Pillar("Polymorphism"));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Pillar to add to inventory.");
            }
        } else if (theItem.getType().equals("CONSUMABLE")) {
            myConsumableItems.add(theItem);
        } else {
            throw new IllegalArgumentException("Invalid item to add to inventory.");
        }
    }

    /**
     * use an item in the inventory
     * @param theItem the item
     * @return was the item used?
     */
    public boolean useItem(final AbstractEquipment theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("The Item is null.");
        }
        final AbstractEquipment firstInstance = containsItem(theItem.getName());
        if (firstInstance != null) {
            if (theItem.getType().equals("CONSUMABLE")) {
                final AbstractConsumable oldConsumable = (AbstractConsumable) firstInstance;
                oldConsumable.triggerEffect();
                if (oldConsumable.getQuantity() <= 0) {
                    myConsumableItems.remove(oldConsumable);
                }
                return true;
            } /*
             else if is permanent{
            } */
            else {
                throw new IllegalArgumentException("The Item has no active ability. Is not CONSUMABLE or PERMANENT");
            }
        } else {
            return false;
        }
    }

    /**
     * Getter for HP potion amount
     * @return How many HP potions in the inventory
     */
    public int getHPPotionAmount() {
        final AbstractEquipment item = containsItem("Health Potion");
        if (item != null) {
            return ((AbstractConsumable)item).getQuantity();
        }
        return 0;
    }
    /**
     * Getter for Vision potion amount
     * @return How many Vision potions in the inventory
     */
    public int getVisionPotionAmount() {
        final AbstractEquipment item = containsItem("Vision Potion");
        if (item != null) {
            return ((AbstractConsumable)item).getQuantity();
        }
        return 0;
    }
}
