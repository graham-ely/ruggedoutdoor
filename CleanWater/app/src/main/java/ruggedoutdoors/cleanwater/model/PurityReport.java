package ruggedoutdoors.cleanwater.model;

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
}
