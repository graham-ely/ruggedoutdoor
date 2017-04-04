package ruggedoutdoors.cleanwater.model;

import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

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

    /**
     * Save this class in a custom save format
     * I chose to use tab (\t) to make line splitting easy for loading
     * If your data had tabs, you would need something else as a delimiter
     *
     * @param writer the file to write this student to
     */
    public void saveAsText(PrintWriter writer) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(getDateTime());
        writer.println(getReporterUsername() + "\t" + getLatitude() + "\t" + getLongitude() + "\t" + waterType.toString() + "\t"
                + waterCondition.toString() + "\t" + getReportNumber() + "\t" + dateStr);
    }


    /**
     * This is a static factory method that constructs a student given a text line in the correct format.
     * It assumes that a student is in a single string with each attribute separated by a tab character
     * The order of the data is assumed to be:
     *
     * 0 - name
     * 1 - user id
     * 2 - id code
     * 3 - email
     * 4 - password
     *
     * @param line  the text line containing the data
     * @return the student object
     */
    public static SourceReport parseEntry(String line) {
        assert line != null;
        String[] tokens = line.split("\t");
        assert tokens.length == 7;
        SourceReport sr = new SourceReport(UserManagementFacade.getInstance().getUserByUsername(tokens[0]),
                new Location(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])),
                WaterType.valueOf(tokens[3].toUpperCase()), WaterCondition.valueOf(tokens[4].toUpperCase()));
        sr.setReportNumber(Integer.parseInt(tokens[5]));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sr.setDateTime(sdf.parse(tokens[6]));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sr;
    }


}
