package ruggedoutdoors.cleanwater.model;

/**
 * Created by karanachtani on 3/23/17.
 */

public enum OverallCondition {
    SAFE {
        @Override
        public String toString() {
            return "SAFE";
        }
    }, TREATABLE {
        @Override
        public String toString() {
            return "TREATABLE";
        }
    }, UNSAFE {
        @Override
        public String toString() {
            return "UNSAFE";
        }
    };
}
