package ruggedoutdoors.cleanwater.model;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Austin Dunn on 4/3/17.
 */

public class Model {

    // database variables
    private static final String TAG = "Model";
    private DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;

    // current user variables
    private static String currFirstName;
    private static String currLastName;
    private static String currUserName;
    private static String currPassword;
    private static String currEmail;
    private static String currPhone;
    private static String currBirthday;
    private static String currAddress;
    private static String currUserType;

    // active report variables
    private static int activeReportNumber;
    private static String activeReporterName;
    private static Date activeDateTime;
    private static double activeLatitude;
    private static double activeLongitude;
    private static WaterType activeWaterType;
    private static WaterCondition activeWaterCondition;
    private static OverallCondition activeOverallCondition;
    private static double activeVPPM;
    private static double activeCPPM;

    // database info variables
    private static String DB_PATH = "/data/data/ruggedoutdoors.cleanwater/databases/";
    private static final String DATABASE_NAME = "model";
    private static final int DATABASE_VERSION = 3;

    // model context variable
    private final Context adapterContext;

    /**
     * default constructor
     */
    public Model() {
        this.adapterContext = null;
    }

    /**
     * constructor
     *
     * @param context Context of where of the model is used
     */
    public Model(Context context) {
        this.adapterContext = context;
    }

    /**
     * opens access to database
     * @throws SQLException
     * @return Model
     */
    public Model open() throws SQLException {
        mDbHelper = new DatabaseHelper(adapterContext);

        try {
            mDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            mDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        return this;
    }

    /**
     * creates tables in database if not already created
     */
    public void init() {
        String command1 = "create table if not exists Users (_id integer primary key autoincrement, " +
                "firstName text, lastName text, username text, password text, " +
                "email text, phone text, birthday text, address text, userType text)";
        mDb.execSQL(command1);
        String command2 = "create table if not exists SourceReports (_id integer primary key autoincrement, " +
                "latitude text, longitude text, waterType text, waterCondition text)";
        mDb.execSQL(command2);
        String command3 = "create table if not exists PurityReports (_id integer primary key autoincrement, " +
                "latitude text, longitude text, overallCondition text, vPPM text, cPPM text)";
        mDb.execSQL(command3);
    }

    /**
     * clear Users table from SQL database
     */
    public void clearUsers() {
        String command = "DELETE FROM Users";
        mDb.execSQL(command);
    }

    /**
     * clear SourceReports table from SQL database
     */
    public void clearSourceReports() {
        String command = "DELETE FROM SourceReports";
        mDb.execSQL(command);
    }

    /**
     * clear PurityReports table from SQL database
     */
    public void clearPurityReports() {
        String command = "DELETE FROM PurityReports";
        mDb.execSQL(command);
    }

    /**
     * Add user to Users table in Database
     *
     * @param firstname string of first name
     * @param lastname string of last name
     * @param username string of username
     * @param password string of password
     * @param email string of email
     * @param phone string of phone
     * @param birthday string of birthday
     * @param address string of address
     * @param userType string of user Type
     */
    public void addUser(String firstname, String lastname, String username, String password,
                        String email, String phone, String birthday, String address,
                        String userType) {
        String command = "INSERT INTO Users (firstName, lastName, username, password, email, " +
                "phone, birthday, address, userType) " + "VALUES ('" + firstname + "', '" +
                lastname + "', '" + username + "', '" + password + "', '" + email + "', " + "'" +
                phone + "', '" + birthday + "', '" + address + "', '" + userType + "')";
        mDb.execSQL(command);
    }

    /**
     * Add report to SourceReports table in Database
     *
     * @param lat string of latitude
     * @param lon string of longitude
     * @param waterType string of water Type
     * @param waterCondition string of water condition
     */
    public void addSourceReport(String lat, String lon, String waterType, String waterCondition) {
        String command = "INSERT INTO SourceReports (latitude, longitude, waterType, " +
                "waterCondition) " + "VALUES ('" + lon + "', '" + lat + "', '" + waterType +
                "', '" + waterCondition + "')";
        mDb.execSQL(command);
    }

    /**
     * Add report to PurityReports table in Database
     *
     * @param lat string of latitude
     * @param lon string of longitude
     * @param overallCondition string of overall condition
     * @param vPPM string of virus PPM
     * @param cPPM string of contaminent PPM
     */
    public void addPurityReport(String lat, String lon, String overallCondition, String vPPM,
                                String cPPM) {
        String command = "INSERT INTO PurityReports (latitude, longitude, OverallCondition, " +
                "vPPM, cPPM) " + "VALUES ('" + lon + "', '" + lat + "', '" + overallCondition +
                "', '" + vPPM + "', '" + cPPM + "')";
        mDb.execSQL(command);
    }

    /**
     * creates array list of source reports from database
     * @return List of Source Reports
     */
    public List<Report> getSourceReportArray() {
        ArrayList<Report> toReturn = new ArrayList<>();
        String query = "SELECT latitude, longitude, waterType, waterCondition FROM SourceReports";
        Cursor c = mDb.rawQuery(query, null);
        try {
            if (c.moveToFirst()){
                do{
                    SourceReport temp = new SourceReport(new User(currFirstName, currLastName,
                            currUserName, currPassword, currEmail, currPhone, currBirthday,
                            currAddress, UserType.valueOf(currUserType)),
                            new Location(Double.parseDouble(c.getString(0)),
                                    Double.parseDouble(c.getString(1))),
                                    WaterType.valueOf(c.getString(2)),
                                    WaterCondition.valueOf(c.getString(3)));
                    toReturn.add(temp);
                }while(c.moveToNext());
            }
        } finally {
            c.close();
        }
        mDb.close();
        return toReturn;
    }

    /**
     * creates array list of purity reports from database
     *
     * @return List<Report> List of Purity Reports
     */
    public List<Report> getPurityReportArray() {
        ArrayList<Report> toReturn = new ArrayList<>();
        String query = "SELECT latitude, longitude, overallCondition, vPPM, cPPM FROM PurityReports";
        Cursor c = mDb.rawQuery(query, null);
        try {
            if (c.moveToFirst()){
                do{
                    PurityReport temp = new PurityReport(new User(currFirstName, currLastName,
                            currUserName, currPassword, currEmail, currPhone, currBirthday,
                            currAddress, UserType.valueOf(currUserType)),
                            new Location(Double.parseDouble(c.getString(0)), Double.parseDouble(c.getString(1))), OverallCondition.valueOf(c.getString(2)), Double.parseDouble(c.getString(3)), Double.parseDouble(c.getString(4)));
                    toReturn.add(temp);
                }while(c.moveToNext());
            }
        } finally {
            c.close();
        }
        mDb.close();
        return toReturn;
    }

    /**
     * adds an active report to the model and its attributes
     *
     * @param report report to set as active
     */
    public void setActiveReport(Report report) {
        activeReportNumber = report.getReportNumber();
        activeReporterName = report.getReporterName();
        activeDateTime = report.getDateTime();
        activeLatitude = report.getLatitude();
        activeLongitude = report.getLongitude();
        if (report instanceof PurityReport) {
            activeOverallCondition = ((PurityReport) report).getOverallCondition();
            activeCPPM = ((PurityReport) report).getContaminantPPM();
            activeVPPM = ((PurityReport) report).getVirusPPM();
        } else {
            activeWaterCondition = ((SourceReport) report).getWaterCondition();
            activeWaterType = ((SourceReport) report).getWaterType();
        }
    }

    /**
     * adds an active report to the model and its attributes
     *
     * @param id id of report to set as active
     */
    public void setActiveReport(int id) {
        List<Report> reports = getSourceReportArray();
        Report report1 = null;
        for (Report r : reports) {
            if (r.getReportNumber() == id) {
                report1 = r;
            }
        }
        activeReportNumber = report1.getReportNumber();
        activeReporterName = report1.getReporterName();
        activeDateTime = report1.getDateTime();
        activeLatitude = report1.getLatitude();
        activeLongitude = report1.getLongitude();
        if (report1 instanceof PurityReport) {
            activeOverallCondition = ((PurityReport) report1).getOverallCondition();
            activeCPPM = ((PurityReport) report1).getContaminantPPM();
            activeVPPM = ((PurityReport) report1).getVirusPPM();
        } else {
            activeWaterCondition = ((SourceReport) report1).getWaterCondition();
            activeWaterType = ((SourceReport) report1).getWaterType();
        }
    }

    public Boolean hasActiveReport() {
        return activeDateTime != null;
    }

    /**
     * gets report number
     * @return active report number
     */
    public int getReportNumber() {
        return activeReportNumber;
    }

    /**
     * gets Date and Time
     * @return active Date and Time
     */
    public String getDateTime() {
        return activeDateTime.toString();
    }

    /**
     * gets latitude
     * @return active latitude
     */
    public double getLatitude() {
        return activeLatitude;
    }

    /**
     * gets longitude
     * @return active longitude
     */
    public double getLongitude() {
        return activeLongitude;
    }

    /**
     * gets water condition
     * @return active water condition
     */
    public String getWaterCondition() {
        return activeWaterCondition.toString();
    }

    /**
     * gets water type
     * @return active water type
     */
    public String getWaterType() {
        return activeWaterType.toString();
    }

    /**
     * gets overall condition
     * @return active overall condition
     */
    public String getOverallCondition() {
        return activeOverallCondition.toString();
    }

    /**
     * gets virus PPM
     * @return active virus PPM
     */
    public double getVirusPPM() {
        return activeVPPM;
    }

    /**
     * gets contaminant PPM
     * @return active contaminent PPM
     */
    public double getContaminantPPM() {
        return activeCPPM;
    }

    /**
     * gets reporter name
     * @return active reporter name
     */
    public String getReporterName() {
        return activeReporterName;
    }

    /**
     * updates user information
     * @param birthday
     * @param email
     * @param phone
     * @param address
     * @param userType
     */
    public void updateUserInfo(String birthday, String email, String phone, String address,
                               String userType) {
        String command = "UPDATE Users SET birthday = '"+ birthday +"', email = '"+ email +
                "', phone = '"+ phone +"', address = '"+ address +"', userType = '"+ userType +
                "' WHERE username = '"+ currUserName +"'";
        mDb.execSQL(command);
    }

    /**
     * checks whether a username is in the database
     * @param username
     * @return whether username is in database
     */
    public Boolean checkUsername(String username) {
        String query = "SELECT username FROM Users";
        Cursor c = mDb.rawQuery(query, null);
        try {
            if (c.moveToFirst()){
                do{
                    String user = c.getString(0);
                    if (user.equals(username)) {
                        c.close();
                        mDb.close();
                        return true;
                    }
                }while(c.moveToNext());
            }
        } finally {
            c.close();
        }
        mDb.close();
        return false;
    }

    /**
     * logs a user into the application
     * @param username
     * @param password
     * @throws InvalidParameterException
     * @throws NoSuchElementException
     * @return whether login was successful
     */
    public Boolean logIn(String username, String password) {
        String query = "SELECT firstName, lastName, username, password, email, phone, birthday, " +
                "address, userType FROM Users";
        Cursor c = mDb.rawQuery(query, null);
        try {
            if (c.moveToFirst()){
                do{
                    String user = c.getString(2);
                    String pass = c.getString(3);
                    if (user.equals(username) && pass.equals(password)) {
                        currFirstName = c.getString(0);
                        currLastName = c.getString(1);
                        currUserName = c.getString(2);
                        currPassword = c.getString(3);
                        currEmail = c.getString(4);
                        currPhone = c.getString(5);
                        currBirthday = c.getString(6);
                        currAddress = c.getString(7);
                        currUserType = c.getString(8);
                        c.close();
                        mDb.close();
                        return true;
                    }
                    if (user.equals(username) && !pass.equals(password)) {
                        c.close();
                        mDb.close();
                        throw new InvalidParameterException("Incorrect Password");
                    }
                }while(c.moveToNext());
            }
        } finally {
            c.close();
        }
        mDb.close();
        throw new NoSuchElementException("User not found.");
    }

    /**
     * gets first name
     * @return current first name
     */
    public String getFirstName() {
        return currFirstName;
    }

    /**
     * gets last name
     * @return current last name
     */
    public String getLastName() {
        return currLastName;
    }

    /**
     * gets username
     * @return current username
     */
    public String getUserName() {
        return currUserName;
    }

    /**
     * gets email
     * @return active email
     */
    public String getEmail() {
        return currEmail;
    }

    /**
     * gets phone
     * @return current phone
     */
    public String getPhone() {
        return currPhone;
    }

    /**
     * gets birthday
     * @return current birthday
     */
    public String getBirthday() {
        return currBirthday;
    }

    /**
     * gets address
     * @return current address
     */
    public String getAddress() {
        return currAddress;
    }

    /**
     * gets user type
     * @return current user type
     */
    public String getUserType() {
        return currUserType;
    }

    /**
     * checks whether current user can file a purity report
     * @return whether user can file purity report
     */
    public boolean canFilePurityReport() {
        return currUserType.equals("WORKER") || currUserType.equals("MANAGER");
    }

    /**
     * checks whether current user can view a purity report
     * @return whether user can view purity report
     */
    public boolean canViewPurityReport() {
        return currUserType.equals("MANAGER");
    }

    /**
     * closes access to database
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * private inner class to access SQL Database
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        // database context
        Context helperContext;

        /**
         * constructor
         *
         * @param context Context of where of the model is used
         */
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            helperContext = context;
        }

        /**
         * defines database
         *
         * @param db SQL database
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        /**
         * handles update of database
         *
         * @param db SQL database
         * @param oldVersion old version of database
         * @param newVersion new version of database
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database!!!!!");
            //db.execSQL("");
            onCreate(db);
        }

        /**
         * creates SQL database
         *
         * @throws IOException
         */
        public void createDataBase() throws IOException {
            boolean dbExist = checkDataBase();
            if (dbExist) {
            } else {
                this.getReadableDatabase();
            }
        }

        /**
         * checks whether database can be accessed
         *
         * @returns whether database can be accessed
         */
        private boolean checkDataBase() {
            SQLiteDatabase checkDB = null;
            try {
                String myPath = DB_PATH + DATABASE_NAME;
                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READONLY);
            } catch (SQLiteException e) {
            }
            if (checkDB != null) {
                checkDB.close();
            }
            return checkDB != null ? true : false;
        }

        /**
         * connect to SQL database
         *
         * @throws SQLException
         */
        public void openDataBase() throws SQLException {
            // Open the database
            String myPath = DB_PATH + DATABASE_NAME;
            mDb = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }

        /**
         * closes connection to database
         */
        @Override
        public synchronized void close() {

            if (mDb != null) {
                mDb.close();
            }

            super.close();

        }
    }

}
