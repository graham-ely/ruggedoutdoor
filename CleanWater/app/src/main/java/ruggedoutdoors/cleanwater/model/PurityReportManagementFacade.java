package ruggedoutdoors.cleanwater.model;


import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * use Case Facade for the user management cases
 *
 * Created by robertwaters on 3/4/16.
 */
public class PurityReportManagementFacade {
    public final static String PURITY_REPORT_TEXT_FILE_NAME = "purityReports.txt";

    /**
     * the facade maintains references to any required model classes
     */
    private PurityReportManager prm;

    /**
     * Singleton pattern
     */
    private static PurityReportManagementFacade instance = new PurityReportManagementFacade();


    /**
     * private constructor for facade pattern
     */
    private PurityReportManagementFacade() {
        prm = new PurityReportManager();
    }

    /**
     * Singleton pattern accessor for instance
     *
     *
     * @return the one and only one instance of this facade
     */
    public static PurityReportManagementFacade getInstance() { return instance; }

    public List<PurityReport> getPurityReportsAsList() {
        return prm.getPurityReports();
    }

    public PurityReport getPurityReportById(final int id) {
        return prm.getPurityReportById(id);
    }

    public void addNewPurityReport(User reporter, Location waterLocation, OverallCondition overallCondition, double virusPPM, double contaminantPPM) {
        prm.addPurityReport( reporter,  waterLocation,  overallCondition,  virusPPM,  contaminantPPM);
    }

    public boolean loadText(File file) {
        try {
            //make an input object for reading
            if (!file.isFile()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            prm.loadFromText(reader);

        } catch (IOException e) {
            Log.e("ModelSingleton", "Failed to open text file for loading!");
            return false;
        }

        return true;
    }



    public boolean saveText(File file) {
        System.out.println("Saving as a text file");
        try {
            PrintWriter pw = new PrintWriter(file);
            prm.saveAsText(pw);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
