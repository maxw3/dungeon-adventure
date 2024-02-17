package model;

public final class MonsterFactory {
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
    public static DungeonCharacter createDummy(){
        return new Dummy();
    }
}
