package ruggedoutdoors.cleanwater.model;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by karanachtani on 3/23/17.
 */

public class PurityReport extends Report {

    private OverallCondition overallCondition;
    private double virusPPM;
    private double contaminantPPM;

    /**
     * Purity Report Constructor
     * @param reporter user reporting
     * @param waterLocation location of the water report
     * @param overallCondition enum value representing the overall condition of the water
     * @param virusPPM virus PPM as a double
     * @param contaminantPPM contaminant PPM as a double
     */
    public PurityReport(User reporter, Location waterLocation, OverallCondition overallCondition, double virusPPM, double contaminantPPM)
    {
        super(reporter, waterLocation);
        this.overallCondition = overallCondition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

    public OverallCondition getOverallCondition() {
        return overallCondition;
    }

    public double getVirusPPM() {
        return virusPPM;
    }

    public double getContaminantPPM() {
        return contaminantPPM;
    }

    @Override
    public String getMapInformation() {
        return "Condition: " + overallCondition.toString() + "\n"
                + "Virus PPM: " + String.format("%.2f", virusPPM) + "\n"
                + "Contaminant PPM" + String.format("%.2f", contaminantPPM);
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
        writer.println(getReporterUsername() + "\t" + getLatitude() + "\t" + getLongitude() + "\t" + overallCondition.toString() + "\t"
                + virusPPM + "\t" + contaminantPPM + "\t" + getReportNumber() + "\t" + dateStr);
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
    public static PurityReport parseEntry(String line) {
        assert line != null;
        String[] tokens = line.split("\t");
        assert tokens.length == 8;
        PurityReport pr = new PurityReport(
                UserManagementFacade.getInstance().getUserByUsername(tokens[0]),
                new Location(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])),
                OverallCondition.valueOf(tokens[3].toUpperCase()),
                Double.parseDouble(tokens[4]),
                Double.parseDouble(tokens[5]));
        pr.setReportNumber(Integer.parseInt(tokens[6]));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            pr.setDateTime(sdf.parse(tokens[7]));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return pr;
    }
}
