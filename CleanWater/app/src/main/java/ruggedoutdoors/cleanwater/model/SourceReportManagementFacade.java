package ruggedoutdoors.cleanwater.model;


import android.util.Log;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * use Case Facade for the user management cases
 *
 * Created by robertwaters on 3/4/16.
 */
public class SourceReportManagementFacade {
    public final static String SOURCE_REPORT_TEXT_FILE_NAME = "sourceReports.txt";

    /**
     * the facade maintains references to any required model classes
     */
    private SourceReportManager srm;

    /**
     * Singleton pattern
     */
    private static SourceReportManagementFacade instance = new SourceReportManagementFacade();


    /**
     * private constructor for facade pattern
     */
    private SourceReportManagementFacade() {
        srm = new SourceReportManager();
    }

    /**
     * Singleton pattern accessor for instance
     *
     *
     * @return the one and only one instance of this facade
     */
    public static SourceReportManagementFacade getInstance() { return instance; }

    public List<SourceReport> getSourceReportsAsList() {
        return srm.getSourceReports();
    }

    public SourceReport getSourceReportById(final int id) {
        return srm.getSourceReportById(id);
    }

    public void addNewSourceReport(User reporter, Location waterLocation, WaterType waterType, WaterCondition waterCondition) {
        srm.addSourceReport( reporter,  waterLocation,  waterType,  waterCondition);
    }

    public boolean loadText(File file) {
        try {
            //make an input object for reading
            if (!file.isFile()) {
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create file");
                }
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            srm.loadFromText(reader);

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
            srm.saveAsText(pw);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
