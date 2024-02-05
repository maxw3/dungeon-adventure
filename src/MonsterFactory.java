public final class MonsterFactory {
    private MonsterFactory() { throw new IllegalStateException("Utility Class");}

    public static DungeonCharacter createSkeleton(final int theFloor){
        return new Skeleton(theFloor);
    }
}
