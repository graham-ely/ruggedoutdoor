package ruggedoutdoors.cleanwater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Austin Dunn on 3/6/2017.
 */

public class Reports {
    private static HashMap<Integer, Report> reportSet = new HashMap<>();

    /**
     * adds a report to the map of reports
     *
     * @param r report to be added
     */
    public static void add(Report r) {
        reportSet.put(r.getReportNumber(), r);
    }

    /**
     * checks if report exists in the map
     *
     * @param reportNumber of report to check
     * @return whether the report is in the map or not
     */
    public static boolean hasReport(int reportNumber) {
        return reportSet.containsKey(reportNumber);
    }

    /**
     * gets a report by report number
     *
     * @param reportNumber of report to get
     * @return the Report object that matches the reportNumber passed in
     * @throws NoSuchElementException if the report does not exist in the map
     */
    public static Report getReport(int reportNumber) {
        if (hasReport(reportNumber)) {
            return reportSet.get(reportNumber);
        } else {
            throw new NoSuchElementException("Report not found.");
        }

    }

    public static List<Report> getReportArray() {
        ArrayList<Report> reports = new ArrayList<Report>();
        for (Report report : reportSet.values()) {
            reports.add(report);
        }
        Collections.sort(reports);
        return reports;
    }
}
