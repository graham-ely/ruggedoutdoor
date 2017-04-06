package ruggedoutdoors.cleanwater.model;

/**
 * Created by gde on 3/3/17.
 * Modified by Austin Dunn on 3/13/17.
 */

public enum WaterCondition {
    WASTE {
        @Override
        public String toString() {
            return "WASTE";
        }
    },
    TREATABLECLEAR {
        @Override
        public String toString() { return "TREATABLECLEAR"; }
    },
    TREATABLEMUDDY {
        @Override
        public String toString() {
            return "TREATABLEMUDDY";
        }
    },
    POTABLE {
        @Override
        public String toString() {
            return "POTABLE";
        }
    }
}