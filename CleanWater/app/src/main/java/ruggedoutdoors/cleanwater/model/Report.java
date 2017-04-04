package ruggedoutdoors.cleanwater.model;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.Random;

/**
 * Created by gde on 3/2/17.
 * Modified by Austin Dunn on 3/13/17
 * Represents a water report filled out by a system user.
 */

public abstract class Report implements Comparable{
    private Date dateTime;

    /* Compares the report to another object
     *
     * @param o            object to be compared
     * @return boolean     true if reports match, false otherwise
     */
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Report)) {
            throw new InvalidParameterException("Can't pass in non reports.");
        }

        Report that = (Report) o;
        if (this.reportNumber < that.reportNumber) {
            return -1;
        } else if (this.reportNumber > that.reportNumber) {
            return 1;
        } else {
            return 0;
        }
    }

    private int reportNumber;
    private static int reportsTotal = 0;
    private User reporter;
    private Location waterLocation;
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
    public Report(User reporter, Location waterLocation)
    {
        this.dateTime = new Date();
        this.reportNumber = (new Random()).nextInt(9999);
        this.reporter = reporter;
        this.waterLocation = waterLocation;
    }

    /**
     * Gets the dateTime that the report was created
     *
     * @return this.dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Sets the dateTime that the report was created
     *
     * @param dt   new dateTime to initiatialize
     */
    public void setDateTime(Date dt) {
        dateTime = dt;
    }

    /**
     * Returns the report number
     *
     * @return reportNumber     the report number
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Sets the report number
     *
     * @param rnum    new report number
     */
    public void setReportNumber(int rnum) {
        reportNumber = rnum;
    }

    /**
     * Returns the name of the reporter
     *
     * @return String    string representation of the first name and last name separated by a space
     */
    public String getReporterName() {
        return ( reporter.getFirstName() + " " + reporter.getLastName() );
    }

    public String getReporterUsername() {
        return reporter.getUsername();
    }

    /**
     * Sets the reporter name
     *
     * @param firstName    first name of the reporter
     * @param lastName     last name of the reporter
     */
    public void setReporterName(String firstName, String lastName) {
        reporter.setFirstName(firstName);
        reporter.setLastName(lastName);
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

    /**
     * Returns the total number of reports in the system
     *
     * @return reportsTotal    total number of reports in the system
     */
    public int getReportsTotal() {
        return reportsTotal;
    }

    /**
     * Returns the latitude of the report
     *
     * @return latitude of the report
     */
    public double getLatitude() {
        return waterLocation.getLatitude();
    }

    /**
     * Returns the longitude of the report
     *
     * @return longitude of the report
     */
    public double getLongitude() {
        return waterLocation.get_longitude();
    }

    public abstract String getMapInformation();

}
