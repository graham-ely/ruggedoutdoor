package ruggedoutdoors.cleanwater.model;

/**
 * Created by gde on 3/3/17.
 * Modified by Austin Dunn 3/13/17
 */

public enum WaterType {
    BOTTLED {
        @Override
        public String toString() {
            return "Bottled";
        }
    },
    WELL {
        @Override
        public String toString() { return "Well"; }
    },
    STREAM {
        @Override
        public String toString() {
            return "Stream";
        }
    },
    LAKE {
        @Override
        public String toString() {
            return "Lake";
        }
    },
    SPRING {
        @Override
        public String toString() {
            return "Spring";
        }
    },
    OTHER {
        @Override
        public String toString() {
            return "Other";
        }
    }
}