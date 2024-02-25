package model;

import java.util.ArrayList;

public class Inventory {
    private final String myNewLine = System.lineSeparator()
    private final AbstractEquipment[] myConsumableItems;
    private final AbstractEquipment[] myPillars;


    public Inventory() {
        myConsumableItems = new AbstractEquipment[2];
        myConsumableItems[0] = new HealthPotion;
        myConsumableItems[1] = new VisionPotion;
        myPillars = new AbstractEquipment[4];
    }

    public String toString() {
        int size = myConsumableItems[0].getQuantity + myConsumableItems[1].getQuantity;

        StringBuilder output = new StringBuilder();
        output.append("You have ");
        output.append(myConsumableItems[0].getQuantity);
        output.append(" health potions and ");
        output.append(myConsumableItems[1].getQuantity);
        output.append(" vision potions for a total of ");
        output.append(size);
        output.append(" items.");

        output.append(myNewLine);

        output.append("Pillar of Abstraction: ");
        output.append(pillarStatus(myPillars[0]));
        output.append(myNewLine);
        output.append("Pillar of Encapsulation: ");
        output.append(pillarStatus(myPillars[1]));
        output.append(myNewLine);
        output.append("Pillar Inheritance: ");
        output.append(pillarStatus(myPillars[2]));
        output.append(myNewLine);
        output.append("Pillar of Polymorphism: ");
        output.append(pillarStatus(myPillars[3]));

        return output.toString();
    }
    private String pillarStatus(final AbstractEquipment thePillar){
        if (thePillar == null){
            return "X";
        } else {
            return "Obtained";
        }
    }

    public String getContents() {
        return myConsumableItems.toString();
    }
    public int getSize() {
        return myConsumableItems.size() + myPillars.length;
    }

    public AbstractEquipment getItem(final int theIndex) {
        if (theIndex >= myConsumableItems.size()) {
            throw new IllegalArgumentException("The index is out of bounds: Is " + theIndex);
        }
        return myConsumableItems.get(theIndex);
    }

    public void addItem(final AbstractEquipment theItem) {
        if (theItem.getType().equals("CONSUMABLE")) {
            if (theItem.getName() = "Health Potion"){
                (AbstractComsumable) myConsumableItems[0].add();
            } else if (theItem.getName() = "Vision Potion"){
                (AbstractComsumable) myConsumableItems[1].add();
            } else {
                throw new IllegalArgumentException("Invalid Consumable to add to inventory.");
            }
        } else if (theItem.getType().equals("PILLAR")) {
            switch(theItem.getName()) {
                case "Abstraction":
                    myPillars[0] = new Pillar("Abstraction");
                    break;
                case "Encapsulation":
                    myPillars[1] = new Pillar("Encapsulation");
                    break;
                case "Inheritance":
                    myPillars[2] = new Pillar("Inheritance");
                    break;
                case "Polymorphism":
                    myPillars[3] = new Pillar("Polymorphism");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Pillar to add to inventory.");
            }
        } else {
            throw new IllegalArgumentException("Invalid item to add to inventory.");
        }
    }

    public void useItem(final AbstractEquipment theItem) {
        if (theItem.getType().equals("CONSUMABLE")) {
            ((AbstractConsumable) theItem).triggerEffect();
        } /* else if (theItem.getType().equals("PERMANENT")){
               example code here
        } */
        else {
            throw new IllegalArgumentException("The Item has no active ability. Is not CONSUMABLE or PERMANENT");
        }
    }
}
