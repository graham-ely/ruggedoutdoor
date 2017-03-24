package ruggedoutdoors.cleanwater.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karanachtani on 3/19/17.
 */

public class Model {

    //Singleton attributes

    private static Model singleton;

    public static Model getInstance() {
        if (singleton == null) {
            singleton = new Model();
        }
        return singleton;
    }


    // instance attributes
    private User currentUser;
    private Report activeReport;

    public boolean logIn(String username, String password) {
        try {
            currentUser = Users.getUser(username, password);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public String getUsername() {
        return currentUser.getUsername();
    }

    public String getFirstName() {
        return currentUser.getFirstName();
    }

    public String getLastName() {
        return currentUser.getLastName();
    }

    public String getAddress() {
        return currentUser.getAddress();
    }

    public String getBirthday() {
        return currentUser.getBirthday();
    }

    public String getPhone() {
        return currentUser.getPhone();
    }

    public String getEmail() {
        return currentUser.getEmail();
    }

    public String getUserType() {
        return currentUser.getUserType().toString();
    }

    public boolean addSourceReport(double latitude, double longitude, String waterType,
                             String waterCondition) {
        Reports.add(new SourceReport(currentUser, new Location(latitude, longitude),
                WaterType.valueOf(waterType.toUpperCase()), WaterCondition.valueOf(waterCondition.toUpperCase())));
        return true;
    }

    public boolean addPurityReport(double latitude, double longitude,
                                   String overallCondition, double virusPPM, double contaminantPPM) {
        Reports.add(new PurityReport(currentUser, new Location(latitude, longitude),
                OverallCondition.valueOf(overallCondition), virusPPM, contaminantPPM));
        return true;
    }

    public Model logOut() {
        singleton = new Model();
        return singleton;
    }

    public void updateUserInfo(String birthday, String email, String phone, String address, String userType) {
        currentUser.setAddress(address);
        currentUser.setBirthday(birthday);
        currentUser.setPhone(phone);
        currentUser.setEmail(email);
        currentUser.setUserType(UserType.valueOf(userType));
    }

    public boolean checkIfUserExists(String username) {
        return Users.hasUser(username);
    }

    public void addUser(String firstName, String lastName, String username, String password,
                        String email, String phone, String birthday, String address, String type) {
        Users.add(new User(firstName, lastName, username, password, email, phone, birthday,
                address, UserType.valueOf(type)));
    }

    public void setActiveReport(int reportId) {
        activeReport = Reports.getReport(reportId);
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
            return ((SourceReport) activeReport).getWaterCondition().toString();
        } else if (activeReport instanceof PurityReport) {
            return ((PurityReport) activeReport).getOverallCondition().toString();
        }
        return "";
    }

    public String getWaterType() {
        return ((SourceReport) activeReport).getWaterType().toString();
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

    public List<Report> getSourceReportArray() {
        List<Report> reports = Reports.getReportArray();
        ArrayList<Report> toReturn = new ArrayList<>();
        for (Report r : reports) {
            if ((r instanceof SourceReport)) {
                toReturn.add(r);
            }
        }
        return toReturn;
    }

    public List<Report> getPurityReportArray() {
        List<Report> reports = Reports.getReportArray();
        ArrayList<Report> toReturn = new ArrayList<>();
        for (Report r : reports) {
            if ((r instanceof PurityReport)) {
                toReturn.add(r);
            }
        }
        return toReturn;
    }

}
