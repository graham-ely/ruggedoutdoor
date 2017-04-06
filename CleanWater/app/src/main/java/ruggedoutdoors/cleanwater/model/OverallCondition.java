package ruggedoutdoors.cleanwater.model;

/**
 * Created by karanachtani on 3/23/17.
 */

public enum OverallCondition {
    SAFE {
        @Override
        public String toString() {
            return "Safe";
        }
    }, TREATABLE {
        @Override
        public String toString() {
            return "Treatable";
        }
    }, UNSAFE {
        @Override
        public String toString() {
            return "Unsafe";
        }
    };
}
