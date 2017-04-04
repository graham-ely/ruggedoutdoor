package ruggedoutdoors.cleanwater.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages the list of students in the model
 * It is all package vis because it should only be accessed from the facade, not directly
 *
 */
class PurityReportManager implements Serializable {

    /**
     * A list of students
     */
    private final List<PurityReport> purityReports = new ArrayList<>();

    /**
     * A map of students by Key == user name Value == student object
     *
     * This is marked as transient so it will not be serialized.
     * It is derived from the students collection above, so it does not
     * need to be serialized.
     */
    private transient Map<Integer, PurityReport> purityReportMap = new HashMap<>();


    void addPurityReport(User reporter, Location waterLocation, OverallCondition overallCondition, double virusPPM, double contaminantPPM) {
        PurityReport pr = new PurityReport( reporter,  waterLocation,  overallCondition,  virusPPM,  contaminantPPM);
        purityReports.add(pr);
        purityReportMap.put(pr.getReportNumber(), pr);
    }


    /**
     * this is package vis because only model should be asking for this data
     *
     * @return
     */
    List<PurityReport> getPurityReports() {
        return purityReports;
    }

    PurityReport getPurityReportById(int id) {
        return purityReportMap.get(id);
    }

    /**
     *
     * @param writer
     */
    void saveAsText(PrintWriter writer) {
        System.out.println("Manager saving: " + purityReports.size() + " purityReports" );
        writer.println(purityReports.size());
        for(PurityReport s : purityReports) {
            s.saveAsText(writer);
        }
    }

    /**
     * load the model from a custom text file
     *
     * @param reader the file to read from
     */
    void loadFromText(BufferedReader reader) {
        System.out.println("Loading Text File");
        purityReportMap.clear();
        purityReports.clear();
        try {
            String countStr = reader.readLine();
            assert countStr != null;
            int count = Integer.parseInt(countStr);

            //then read in each user to model
            for (int i = 0; i < count; ++i) {
                String line = reader.readLine();
                PurityReport s = PurityReport.parseEntry(line);
                purityReports.add(s);
                purityReportMap.put(s.getReportNumber(), s);
            }
            //be sure and close the file
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done loading text file with " + purityReports.size() + " source reports");

    }


    /**
     * This should only be called during serialization (reading in).
     *
     * This recomputes the student map which is derived from the student collection.
     *
     */
    void regenMap() {
        if (purityReportMap != null)
            purityReportMap.clear();
        else
            purityReportMap = new HashMap<>();
        for (PurityReport s : purityReports) {
            purityReportMap.put(s.getReportNumber(), s);
        }
    }


}
