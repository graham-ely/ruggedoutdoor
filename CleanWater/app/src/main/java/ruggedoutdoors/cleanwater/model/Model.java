package ruggedoutdoors.cleanwater.model;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
import java.io.File;

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
        umf.loadText(new File(UserManagementFacade.DEFAULT_TEXT_FILE_NAME));
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
     * @param latitude latitude coordinate for report location
     * @param longitude longitude coordinate for report location
     * @param waterType water type of report
     * @param waterCondition water condition of report
     * @return whether it was added or not
     */
    public boolean addSourceReport(double latitude, double longitude, String waterType,
                             String waterCondition) {
        try {
            srmf.addNewSourceReport(currentUser, new Location(latitude, longitude),
                    WaterType.valueOf(waterType.toUpperCase()), WaterCondition.valueOf(waterCondition.toUpperCase()));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * adds a new purity report to the model
     * @param latitude latitude coordinate for report location
     * @param longitude longitude coordinate for report location
     * @param overallCondition overall condition of report
     * @param virusPPM virus PPM of report
     * @param contaminantPPM contaminant of report
     * @return whether it was added or not
     */
    public boolean addPurityReport(double latitude, double longitude,
                                   String overallCondition, double virusPPM, double contaminantPPM) {
        try {
            prmf.addNewPurityReport(currentUser, new Location(latitude, longitude),
                    OverallCondition.valueOf(overallCondition), virusPPM, contaminantPPM);
            return true;
        } catch (Exception e) {
            return false;
        }


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
     * @param birthday new birthday of user
     * @param email new email of user
     * @param phone new phone of user
     * @param address new address of user
     * @param userType new user type of user
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
     * @param firstName first name of new user
     * @param lastName last name of new user
     * @param username username of new user
     * @param password password of new user
     * @param email email of new user
     * @param phone phone of new user
     * @param birthday birthday of new user
     * @param address address of new user
     * @param type type of new user
     */
    public void addUser(String firstName, String lastName, String username, String password,
                        String email, String phone, String birthday, String address, String type) {

        umf.addNewUser(firstName, lastName, username, password, email, phone, birthday,
                address, type);
    }

    /**
     * saves user data
     * @param file file to save data to
     */
    public void saveUserData(File file) {
        umf.saveText(file);
    }

    /**
     * loads user data
     * @param file file to load data from
     */
    public void loadUserData(File file) {
        umf.loadText(file);
    }

    /**
     * saves source report data
     * @param file file to save data to
     */
    public void saveSourceReportData(File file) {
        srmf.saveText(file);
    }

    /**
     * loads source report data
     * @param file file to load data from
     */
    public void loadSourceReportData(File file) {
        srmf.loadText(file);
    }

    /**
     * saves purity report data
     * @param file file to save data to
     */
    public void savePurityReportData(File file) {
        prmf.saveText(file);
    }

    /**
     * loads purity report data
     * @param file file to load data from
     */
    public void loadPurityReportData(File file) {
        prmf.loadText(file);
    }

    /**
     * sets active source report
     * @param reportId report id of report to set active
     */
    public void setActiveSourceReport(int reportId) {
        activeReport = srmf.getSourceReportById(reportId);
    }

    /**
     * sets active purity report
     * @param reportId report id of report to set active
     */
    public void setActivePurityReport(int reportId) {
        activeReport = prmf.getPurityReportById(reportId);
    }

    /**
     * checks for an active report
     * @return whether there is an active report
     */
    public boolean hasActiveReport() {
        return activeReport != null;
    }

    /**
     * gets active report number
     * @return report number
     */
    public int getReportNumber() {
            return activeReport.getReportNumber();
    }

    /**
     * gets active report reporter name
     * @return reporter name
     */
    public String getReporterName() {
        return activeReport.getReporterName();
    }

    /**
     * gets active report latitude
     * @return latitude
     */
    public double getLatitude() {
        return activeReport.getLatitude();
    }

    /**
     * gets active report longitude
     * @return longitude
     */
    public double getLongitude() {
        return activeReport.getLongitude();
    }

    /**
     * gets active report date and time
     * @return date and time
     */
    public String getDateTime() {
        return activeReport.getDateTime().toString();
    }

    /**
     * gets active report water condition
     * @return water condition
     */
    public String getWaterCondition() {
        if (activeReport instanceof SourceReport) {
            return activeReport.getWaterCondition().toString();
        } else if (activeReport instanceof PurityReport) {
            return ((PurityReport) activeReport).getOverallCondition().toString();
        }
        return "";
    }

    /**
     * gets active report water type
     * @return water type
     */
    public String getWaterType() {
        return activeReport.getWaterType().toString();
    }

    /**
     * gets active report virus PPM
     * @return virus PPM
     */
    public double getVirusPPM() {
        return ((PurityReport) activeReport).getVirusPPM();
    }

    /**
     * gets active report contaminant PPM
     * @return contaminant PPM
     */
    public double getContaminantPPM() {
        return ((PurityReport) activeReport).getContaminantPPM();
    }

    /**
     * checks whether current user can file purity reports
     * @return whether current user can file purity reports
     */
    public boolean canFilePurityReport() {
        return currentUser.getUserType() == UserType.WORKER || currentUser.getUserType() == UserType.MANAGER;
    }

    /**
     * checks whether current user can view purity reports
     * @return whether current user can view purity reports
     */
    public boolean canViewPurityReport() {
        return currentUser.getUserType() == UserType.MANAGER;
    }

    /**
     * checks whether current user can view graph
     * @return whether current user can view graph
     */
    public boolean canViewHistoryGraph() {
        return currentUser.getUserType() == UserType.MANAGER;
    }

    /**
     * gets list of source reports
     * @return list of source reports
     */
    public List<SourceReport> getSourceReportArray() {
        return srmf.getSourceReportsAsList();
    }

    /**
     * gets list of purity report
     * @return list of purity reports
     */
    public List<PurityReport> getPurityReportArray() {
        return prmf.getPurityReportsAsList();
    }

}
