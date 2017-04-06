package ruggedoutdoors.cleanwater.model;

/**
 * Created by gde on 3/2/17.
 * Modified by Austin Dunn on 3/13/17
 * Represents a water report filled out by a system user.
 */

public class SourceReport extends Report{

    private WaterType waterType;
    private WaterCondition waterCondition;

    /*
     * Creates a new Report
     * @param waterType      - enum value representing water source
     * @param waterCondition - enum value representing water condition
     */
    public SourceReport(User reporter, Location waterLocation, WaterType waterType, WaterCondition waterCondition)
    {
        super(reporter, waterLocation);
        this.waterType = waterType;
        this.waterCondition = waterCondition;
    }


    /**
     * Returns the water type at the location
     *
     * @return waterType    the water type at the location
     */
    public WaterType getWaterType() {
        return waterType;
    }

    /**
     * Sets the water type at the location
     *
     * @param wt    water type
     */
    public void setWaterType(WaterType wt) {
        waterType = wt;
    }

    /**
     * Returns the water condition at the location
     *
     * @return waterCondition    the water condition at the location
     */
    public WaterCondition getWaterCondition() {
        return waterCondition;
    }

    /**
     * Sets the water condition
     *
     * @param wc    new water condition
     */
    public void setWaterCondition(WaterCondition wc) {
        waterCondition = wc;
    }

    @Override
    public String getMapInformation() {
        return "Condition: " + waterCondition.toString() + "\n"
                + "Type: " + waterType.toString();
    }
}
