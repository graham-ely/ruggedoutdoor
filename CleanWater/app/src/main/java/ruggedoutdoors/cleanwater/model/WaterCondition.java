package ruggedoutdoors.cleanwater.model;

/**
 * Created by gde on 3/3/17.
 * Modified by Austin Dunn on 3/13/17.
 */

public enum WaterCondition {
    WASTE {
        @Override
        public String toString() {
            return "Waste";
        }
    },
    TREATABLECLEAR {
        @Override
        public String toString() { return "Treatable and Clear"; }
    },
    TREATABLEMUDDY {
        @Override
        public String toString() {
            return "Treatable and Muddy";
        }
    },
    POTABLE {
        @Override
        public String toString() {
            return "Potable";
        }
    }
}