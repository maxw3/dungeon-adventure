/*
 *  Dungeon Adventure Project for TCSS 360
 *  Winter 2024, Jordan, Terence, Max, and Gabriel
 */

package enums;

public enum Direction {
    NORTH {
        @Override
        public Direction getOpposite() {
            return SOUTH;
        }
    },
    SOUTH {
        @Override
        public Direction getOpposite() {
            return NORTH;
        }
    },
    EAST {
        @Override
        public Direction getOpposite() {
            return WEST;
        }
    },
    WEST {
        @Override
        public Direction getOpposite() {
            return EAST;
        }
    },;

    public abstract Direction getOpposite();
}
