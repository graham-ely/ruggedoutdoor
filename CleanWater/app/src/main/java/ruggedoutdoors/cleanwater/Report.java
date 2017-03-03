package ruggedoutdoors.cleanwater;

import java.util.Date;

/**
 * Created by gde on 3/2/17.
 * Represents a water report filled out by a system user.
 */

public class Report {
    private Date dateTime;
    private int reportNumber;
    private static int reportsTotal;
    private String reporterName;
    private String waterLocation;
    private WaterType waterType;
    private WaterCondition waterCondition;

    /*
     * Creates a new Report
     *
     * @param reporterName   - name of the user who submitted the report
     * @param waterLocation  - string value representing water location
     * @param waterType      - enum value representing water source
     * @param waterCondition - enum value representing water condition
     */
    public Report(String reporterName, String waterLocation, WaterType waterType, WaterCondition waterCondition)
    {
        this.dateTime = new Date();
        this.reportNumber = reportsTotal++;
        this.reporterName = reporterName;
        this.waterLocation = waterLocation;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
    }
}
