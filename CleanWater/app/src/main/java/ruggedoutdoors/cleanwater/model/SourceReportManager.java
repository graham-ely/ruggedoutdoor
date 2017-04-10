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
class SourceReportManager implements Serializable {

    /**
     * A list of students
     */
    private final List<SourceReport> sourceReports = new ArrayList<>();

    /**
     * A map of students by Key == user name Value == student object
     *
     * This is marked as transient so it will not be serialized.
     * It is derived from the students collection above, so it does not
     * need to be serialized.
     */
    private transient Map<Integer, SourceReport> sourceReportMap = new HashMap<>();


    void addSourceReport(User reporter, Location waterLocation, WaterType waterType, WaterCondition waterCondition) {
        SourceReport sr = new SourceReport(reporter, waterLocation, waterType, waterCondition);
        sourceReports.add(sr);
        sourceReportMap.put(sr.getReportNumber(), sr);
    }


    /**
     * this is package vis because only model should be asking for this data
     *
     * @return a list of all source reports
     */
    List<SourceReport> getSourceReports() {
        return sourceReports;
    }

    SourceReport getSourceReportById(int id) {
        return sourceReportMap.get(id);
    }

    /**
     *
     * @param writer writer to use for saving
     */
    void saveAsText(PrintWriter writer) {
        System.out.println("Manager saving: " + sourceReports.size() + " sourceReports" );
        writer.println(sourceReports.size());
        for(SourceReport s : sourceReports) {
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
        sourceReportMap.clear();
        sourceReports.clear();
        try {
            String countStr = reader.readLine();
            assert countStr != null;
            int count = Integer.parseInt(countStr);

            //then read in each user to model
            for (int i = 0; i < count; ++i) {
                String line = reader.readLine();
                SourceReport s = SourceReport.parseEntry(line);
                sourceReports.add(s);
                sourceReportMap.put(s.getReportNumber(), s);
            }
            //be sure and close the file
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done loading text file with " + sourceReports.size() + " source reports");

    }


    /**
     * This should only be called during serialization (reading in).
     *
     * This recomputes the student map which is derived from the student collection.
     *
     */
    void regenMap() {
        if (sourceReportMap != null)
            sourceReportMap.clear();
        else
            sourceReportMap = new HashMap<>();
        for (SourceReport s : sourceReports) {
            sourceReportMap.put(s.getReportNumber(), s);
        }
    }
}
