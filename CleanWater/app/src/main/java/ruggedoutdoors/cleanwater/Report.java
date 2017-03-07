package ruggedoutdoors.cleanwater;

import java.util.Date;

/**
 * Created by gde on 3/2/17.
 * Modified by Austin Dunn on 3/6/17
 * Represents a water report filled out by a system user.
 */

public class Report {
    private Date dateTime;
    private int reportNumber;
    private static int reportsTotal = 0;
    private User reporter;
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
    public Report(User reporter, String waterLocation, WaterType waterType, WaterCondition waterCondition)
    {
        this.dateTime = new Date();
        this.reportNumber = reportsTotal++;
        this.reporter = reporter;
        this.waterLocation = waterLocation;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dt) {
        dateTime = dt;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int rnum) {
        reportNumber = rnum;
    }

    public String getReporterName() {
        return ( reporter.getFirstName() + " " + reporter.getLastName() );
    }

    public void setReporterName(String firstName, String lastName) {
        reporter.setFirstName(firstName);
        reporter.setLastName(lastName);
    }

    public String getWaterLocation() {
        return waterLocation;
    }

    public void setWaterLocation(String wl) {
        waterLocation = wl;
    }

    public WaterType getWaterType() {
        return waterType;
    }

    public void setWaterType(WaterType wt) {
        waterType = wt;
    }

    public WaterCondition getWaterCondition() {
        return waterCondition;
    }

    public void setWaterCondition(WaterCondition wc) {
        waterCondition = wc;
    }

    public int getReportsTotal() {
        return reportsTotal;
    }

}
