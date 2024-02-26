package model;

import java.util.Random;

public final class MonsterFactory {
    private static final Random RANDOM = new Random();
    private static final int NUM_OF_MONSTER_TYPES = 5;
    private MonsterFactory() { throw new IllegalStateException("Utility Class"); }

    public static AbstractDungeonCharacter createSkeleton(final int theFloor) {
        return new Skeleton(theFloor);
    }
    public static AbstractDungeonCharacter createGremlin(final int theFloor) {
        return new Gremlin(theFloor);
    }
    public static AbstractDungeonCharacter createOgre(final int theFloor) {
        return new Ogre(theFloor);
    }
    public static AbstractDungeonCharacter createOrc(final int theFloor) {
        return new Orc(theFloor);
    }
    public static AbstractDungeonCharacter createTroll(final int theFloor) {
        return new Troll(theFloor);
    }
    public static AbstractDungeonCharacter createMonster(final int theFloor) {
        final int choice = RANDOM.nextInt(NUM_OF_MONSTER_TYPES);
        if (choice == 0) {
            return new Skeleton(theFloor);
        } else if (choice == 1) {
            return new Gremlin(theFloor);
        } else if (choice == 2) {
            return new Ogre(theFloor);
        } else if (choice == 3) {
            return new Orc(theFloor);
        } else {
            return new Troll(theFloor);
        }
    }
    public static AbstractDungeonCharacter createDummy() {
        return new Dummy();
    }
}
