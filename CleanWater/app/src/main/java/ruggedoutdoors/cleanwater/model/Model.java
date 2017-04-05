package ruggedoutdoors.cleanwater.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.io.File;
import android.content.Context;

/**
 * Created by karanachtani on 3/19/17.
 */

public class Model {

    //Singleton attributes

    private static Model singleton;

    /**
     * gets the single instance of the model
     * @return the instance of the model
     */
    public static Model getInstance() {
        if (singleton == null) {
            singleton = new Model();
        }
        return singleton;
    }


    // instance attributes
    private User currentUser;
    private Report activeReport;

    private UserManagementFacade umf = UserManagementFacade.getInstance();
    private SourceReportManagementFacade srmf = SourceReportManagementFacade.getInstance();
    private PurityReportManagementFacade prmf = PurityReportManagementFacade.getInstance();

    /**
     * logs in the user
     * @param username String username
     * @param password String password
     * @return whether the user has been logged in or not
     */
    public boolean logIn(String username, String password) {
        umf.loadJson(new File(UserManagementFacade.DEFAULT_JSON_FILE_NAME));
        User u = umf.getUserByUsername(username);
        if (u == null) {
            throw new NoSuchElementException();
        }
        if (u.checkPassword(password)) {
            currentUser = u;
            return true;
        } else {
            throw new InvalidParameterException();
        }
    }

    public void updateCurrentUser(User u) {
        currentUser = u;
    }

    /**
     *
     * @return username of current user
     */
    public String getUsername() {
        return currentUser.getUsername();
    }

    /**
     *
     * @return first name of current user
     */
    public String getFirstName() {
        return currentUser.getFirstName();
    }

    /**
     *
     * @return last name of current user
     */
    public String getLastName() {
        return currentUser.getLastName();
    }

    /**
     *
     * @return address of current user
     */
    public String getAddress() {
        return currentUser.getAddress();
    }

    /**
     *
     * @return birthday  of current user
     */
    public String getBirthday() {
        return currentUser.getBirthday();
    }

    /**
     *
     * @return phone number of current user
     */
    public String getPhone() {
        return currentUser.getPhone();
    }

    /**
     *
     * @return email of current user
     */
    public String getEmail() {
        return currentUser.getEmail();
    }

    /**
     *
     * @return userType of current user
     */
    public String getUserType() {
        return currentUser.getUserType().toString();
    }

    /**
     * adds a new source report to the model
     * @param latitude
     * @param longitude
     * @param waterType
     * @param waterCondition
     * @return whether it was added or not
     */
    public boolean addSourceReport(double latitude, double longitude, String waterType,
                             String waterCondition) {
        srmf.addNewSourceReport(currentUser, new Location(latitude, longitude),
                WaterType.valueOf(waterType.toUpperCase()), WaterCondition.valueOf(waterCondition.toUpperCase()));
        return true;
    }

    /**
     * adds a new purity report to the model
     * @param latitude
     * @param longitude
     * @param overallCondition
     * @param virusPPM
     * @param contaminantPPM
     * @return whether it was added or not
     */
    public boolean addPurityReport(double latitude, double longitude,
                                   String overallCondition, double virusPPM, double contaminantPPM) {
        prmf.addNewPurityReport(currentUser, new Location(latitude, longitude),
                OverallCondition.valueOf(overallCondition), virusPPM, contaminantPPM);
        return true;
    }

    /**
     * logs the user out of the model and resets the singleton
     * @return the new singleton
     */
    public Model logOut() {
        singleton = new Model();
        return singleton;
    }

    /**
     * updates user information
     * @param birthday
     * @param email
     * @param phone
     * @param address
     * @param userType
     */
    public void updateUserInfo(String birthday, String email, String phone, String address, String userType) {
        currentUser.setAddress(address);
        currentUser.setBirthday(birthday);
        currentUser.setPhone(phone);
        currentUser.setEmail(email);
        currentUser.setUserType(UserType.valueOf(userType));
    }

    /**
     * checks if a given user exists
     * @param username to check
     * @return whether it exists or not
     */
    public boolean checkIfUserExists(String username) {
        return umf.getUserByUsername(username) != null;
    }

    /**
     * adds a new user to the model
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param email
     * @param phone
     * @param birthday
     * @param address
     * @param type
     */
    public void addUser(String firstName, String lastName, String username, String password,
                        String email, String phone, String birthday, String address, String type) {

        umf.addNewUser(firstName, lastName, username, password, email, phone, birthday,
                address, type);
    }

    public void saveUserData(File file) {
        umf.saveText(file);
    }

    public void loadUserData(File file) {
        umf.loadText(file);
    }

    public void saveSourceReportData(File file) {
        srmf.saveText(file);
    }

    public void loadSourceReportData(File file) {
        srmf.loadText(file);
    }

    public void savePurityReportData(File file) {
        prmf.saveText(file);
    }

    public void loadPurityReportData(File file) {
        prmf.loadText(file);
    }

    public void setActiveSourceReport(int reportId) {
        activeReport = srmf.getSourceReportById(reportId);
    }

    public void setActivePurityReport(int reportId) {
        activeReport = prmf.getPurityReportById(reportId);
    }

    public boolean hasActiveReport() {
        return activeReport != null;
    }

    public int getReportNumber() {
            return activeReport.getReportNumber();
    }

    public String getReporterName() {
        return activeReport.getReporterName();
    }

    public double getLatitude() {
        return activeReport.getLatitude();
    }

    public double getLongitude() {
        return activeReport.getLongitude();
    }

    public String getDateTime() {
        return activeReport.getDateTime().toString();
    }

    public String getWaterCondition() {
        if (activeReport instanceof SourceReport) {
            return activeReport.getWaterCondition().toString();
        } else if (activeReport instanceof PurityReport) {
            return ((PurityReport) activeReport).getOverallCondition().toString();
        }
        return "";
    }

    public String getWaterType() {
        return activeReport.getWaterType().toString();
    }

    public double getVirusPPM() {
        return ((PurityReport) activeReport).getVirusPPM();
    }

    public double getContaminantPPM() {
        return ((PurityReport) activeReport).getContaminantPPM();
    }

    public boolean canFilePurityReport() {
        return currentUser.getUserType() == UserType.WORKER || currentUser.getUserType() == UserType.MANAGER;
    }

    public boolean canViewPurityReport() {
        return currentUser.getUserType() == UserType.MANAGER;
    }

    public List<SourceReport> getSourceReportArray() {
        return srmf.getSourceReportsAsList();
    }

    public List<PurityReport> getPurityReportArray() {
        return prmf.getPurityReportsAsList();
    }

}
