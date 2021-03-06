package ruggedoutdoors.cleanwater.model;

import com.google.android.gms.fitness.data.Value;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by karanachtani on 3/19/17.
 */

public class ModelOld {

    //Singleton attributes

    private static ModelOld singleton;

    /**
     * gets the single instance of the model
     * @return the instance of the model
     */
    public static ModelOld getInstance() {
        if (singleton == null) {
            singleton = new ModelOld();
        }
        return singleton;
    }


    // instance attributes
    private User currentUser;
    private Report activeReport;
    private ValueEventListener listener;

    //set up firebase
    private FirebaseDatabase database;
    private DatabaseReference mDatabase = database.getInstance().getReference("users");

    /**
     * logs in the user
     * @param username String username
     * @param password String password
     * @return whether the user has been logged in or not
     */
    public boolean logIn(final String username, final String password) {
        try {
            listener = mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("TEST", "OnDataChange Ran");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getValue(User.class) != null) {
                            setUser(snapshot.getValue(User.class));
                            if (!snapshot.hasChild(getUsername())) {
                                throw new NoSuchElementException("User not found.");
                            } else if (!snapshot.hasChild(getPassword())) {
                                throw new InvalidParameterException("Incorrect Password");
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            mDatabase.removeEventListener(listener);
            return true;
        } catch (Exception e) {
            mDatabase.removeEventListener(listener);
            throw e;
        }
//        try {
//            currentUser = Users.getUser(username);
//            return true;
//        } catch (Exception e) {
//            throw e;
//        }
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
     * @return password of current user
     */
    public String getPassword() {
        return currentUser.getPassword();
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
     * sets currentUser
     * @param user
     */
    public void setUser(User user) {
        this.currentUser = user;
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
        Reports.add(new SourceReport(currentUser, new Location(latitude, longitude),
                WaterType.valueOf(waterType.toUpperCase()), WaterCondition.valueOf(waterCondition.toUpperCase())));
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
        Reports.add(new PurityReport(currentUser, new Location(latitude, longitude),
                OverallCondition.valueOf(overallCondition), virusPPM, contaminantPPM));
        return true;
    }

    /**
     * logs the user out of the model and resets the singleton
     * @return the new singleton
     */
    public ModelOld logOut() {
        singleton = new ModelOld();
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
        return Users.hasUser(username);
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
