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

    private static String DB_PATH = "/data/data/ruggedoutdoors.cleanwater/databases/";

    private static final String DATABASE_NAME = "model";

    private static final int DATABASE_VERSION = 3;

    private final Context adapterContext;

    public Model() {
        this.adapterContext = null;
    }

    public Model(Context context) {
        this.adapterContext = context;
    }

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

    public void clearUsers() {
        String command = "DELETE FROM Users";
        mDb.execSQL(command);
    }

    public void clearSourceReports() {
        String command = "DELETE FROM SourceReports";
        mDb.execSQL(command);
    }

    public void clearPurityReports() {
        String command = "DELETE FROM PurityReports";
        mDb.execSQL(command);
    }

    public void addUser(String firstname, String lastname, String username, String password,
                        String email, String phone, String birthday, String address,
                        String userType) {
        String command = "INSERT INTO Users (firstName, lastName, username, password, email, " +
                "phone, birthday, address, userType) " + "VALUES ('" + firstname + "', '" +
                lastname + "', '" + username + "', '" + password + "', '" + email + "', " + "'" +
                phone + "', '" + birthday + "', '" + address + "', '" + userType + "')";
        mDb.execSQL(command);
    }

    public void addSourceReport(String lat, String lon, String waterType, String waterCondition) {
        String command = "INSERT INTO SourceReports (latitude, longitude, waterType, " +
                "waterCondition) " + "VALUES ('" + lon + "', '" + lat + "', '" + waterType +
                "', '" + waterCondition + "')";
        mDb.execSQL(command);
    }

    public void addPurityReport(String lat, String lon, String overallCondition, String vPPM,
                                String cPPM) {
        String command = "INSERT INTO PurityReports (latitude, longitude, OverallCondition, " +
                "vPPM, cPPM) " + "VALUES ('" + lon + "', '" + lat + "', '" + overallCondition +
                "', '" + vPPM + "', '" + cPPM + "')";
        mDb.execSQL(command);
    }

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

    public int getReportNumber() {
        return activeReportNumber;
    }

    public String getDateTime() {
        return activeDateTime.toString();
    }

    public double getLatitude() {
        return activeLatitude;
    }

    public double getLongitude() {
        return activeLongitude;
    }

    public String getWaterCondition() {
        return activeWaterCondition.toString();
    }

    public String getWaterType() {
        return activeWaterType.toString();
    }

    public String getOverallCondition() {
        return activeOverallCondition.toString();
    }

    public double getVirusPPM() {
        return activeVPPM;
    }

    public double getContaminantPPM() {
        return activeCPPM;
    }

    public String getReporterName() {
        return activeReporterName;
    }

    public void updateUserInfo(String birthday, String email, String phone, String address,
                               String userType) {
        String command = "UPDATE Users SET birthday = '"+ birthday +"', email = '"+ email +
                "', phone = '"+ phone +"', address = '"+ address +"', userType = '"+ userType +
                "' WHERE username = '"+ currUserName +"'";
        mDb.execSQL(command);
    }

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

    public String getFirstName() {
        return currFirstName;
    }

    public String getLastName() {
        return currLastName;
    }

    public String getUserName() {
        return currUserName;
    }

    public String getEmail() {
        return currEmail;
    }

    public String getPhone() {
        return currPhone;
    }

    public String getBirthday() {
        return currBirthday;
    }

    public String getAddress() {
        return currAddress;
    }

    public String getUserType() {
        return currUserType;
    }

    public boolean canFilePurityReport() {
        return currUserType.equals("WORKER") || currUserType.equals("MANAGER");
    }

    public boolean canViewPurityReport() {
        return currUserType.equals("MANAGER");
    }

    public void close() {
        mDbHelper.close();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        Context helperContext;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            helperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database!!!!!");
            //db.execSQL("");
            onCreate(db);
        }

        public void createDataBase() throws IOException {
            boolean dbExist = checkDataBase();
            if (dbExist) {
            } else {
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
                this.getReadableDatabase();
            }
        }

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

        public void openDataBase() throws SQLException {
            // Open the database
            String myPath = DB_PATH + DATABASE_NAME;
            mDb = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }

        @Override
        public synchronized void close() {

            if (mDb != null) {
                mDb.close();
            }

            super.close();

        }
    }

}
