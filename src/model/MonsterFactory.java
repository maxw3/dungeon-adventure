package model;

import java.util.Random;

public final class MonsterFactory {
    private static final Random RANDOM = new Random();
    private static final int NUM_OF_MONSTER_TYPES = 5;
    private MonsterFactory() { throw new IllegalStateException("Utility Class");}

    public static DungeonCharacter createSkeleton(final int theFloor){
        return new Skeleton(theFloor);
    }
    public static DungeonCharacter createGremlin(final int theFloor){
        return new Gremlin(theFloor);
    }
    public static DungeonCharacter createOgre(final int theFloor){
        return new Ogre(theFloor);
    }
    public static DungeonCharacter createOrc(final int theFloor){
        return new Orc(theFloor);
    }
    public static DungeonCharacter createTroll(final int theFloor){
        return new Troll(theFloor);
    }
    public static DungeonCharacter createMonster(final int theFloor){
        int choice = RANDOM.nextInt(NUM_OF_MONSTER_TYPES);
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
    public static DungeonCharacter createDummy(){
        return new Dummy();
    }
}
