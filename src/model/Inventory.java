package model;

import java.util.ArrayList;

public class Inventory {

    private final String myNewLine = System.lineSeparator();
    private final ArrayList<AbstractEquipment> myConsumableItems;
    private final ArrayList<Pillar> myPillars = new ArrayList<>(4);


    public Inventory() {
        myConsumableItems = new ArrayList<>();
        myPillars.add(null);
        myPillars.add(null);
        myPillars.add(null);
        myPillars.add(null);
    }

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
    private String pillarStatus(final AbstractEquipment thePillar){
        if (thePillar == null){
            return "X";
        } else {
            return "Obtained";
        }
    }

    private AbstractEquipment containsItem(final String theName) {
        for (final AbstractEquipment i : myConsumableItems) {
            if (i.getName().equalsIgnoreCase(theName)) {
                return i;
            }
        }
        return null;
    }
    public int getSize() {
        return myConsumableItems.size();
    }

    public AbstractEquipment getItem(final int theIndex) {
        if (theIndex >= myConsumableItems.size()) {
            throw new IllegalArgumentException("The index is out of bounds: Is " + theIndex);
        }
        return myConsumableItems.get(theIndex);
    }

    public void addItem(final AbstractEquipment theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("The Item is null.");
        }
        final AbstractEquipment firstInstance = containsItem(theItem.getName());
        if (firstInstance != null) {
            if (theItem.getType().equals("CONSUMABLE")) {
                final AbstractConsumable oldConsumable = (AbstractConsumable) firstInstance;
                oldConsumable.add();
            }  else {
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

    public void useItem(final AbstractEquipment theItem) {
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
            } /*
             else if is permanent{
            } */
            else {
                throw new IllegalArgumentException("The Item has no active ability. Is not CONSUMABLE or PERMANENT");
            }
        }
    }
    public int getHPPotionAmount() {
        final AbstractEquipment item = containsItem("Health Potion");
        if (item != null) {
            return ((AbstractConsumable)item).getQuantity();
        }
        return 0;
    }
    public int getVisionPotionAmount() {
        final AbstractEquipment item = containsItem("Vision Potion");
        if (item != null) {
            return ((AbstractConsumable)item).getQuantity();
        }
        return 0;
    }
}
