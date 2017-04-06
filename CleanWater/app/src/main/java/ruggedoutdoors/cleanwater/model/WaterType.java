package ruggedoutdoors.cleanwater.model;

/**
 * Created by gde on 3/3/17.
 * Modified by Austin Dunn 3/13/17
 */

public enum WaterType {
    BOTTLED {
        @Override
        public String toString() {
            return "BOTTLED";
        }
    },
    WELL {
        @Override
        public String toString() { return "WELL"; }
    },
    STREAM {
        @Override
        public String toString() {
            return "STREAM";
        }
    },
    LAKE {
        @Override
        public String toString() {
            return "LAKE";
        }
    },
    SPRING {
        @Override
        public String toString() {
            return "SPRING";
        }
    },
    OTHER {
        @Override
        public String toString() {
            return "OTHER";
        }
    }
}