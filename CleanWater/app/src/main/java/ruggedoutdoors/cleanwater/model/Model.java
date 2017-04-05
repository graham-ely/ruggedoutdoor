package ruggedoutdoors.cleanwater.model;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

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

    private static final String TAG = "AnyDBAdapter";
    private DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;
    private static String currUserType;

    private static String DB_PATH = "/data/data/ruggedoutdoors.cleanwater/databases/";

    private static final String DATABASE_NAME = "model";

    private static final int DATABASE_VERSION = 3;

    private final Context adapterContext;

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

    public Cursor ExampleSelect()
    {
        String query = "SELECT username FROM Users";
        return mDb.rawQuery(query, null);
    }

    public void ExampleCommand(String myVariable1)
    {
//        String command = "create table Users (_id integer primary key autoincrement, " +
//                "firstName text, lastName text, username text, password text, " +
//                "email text, phone text, birthday text, address text, userType text)";
        String command = "INSERT INTO Users (firstName, lastName, username, password, email, phone, birthday, address, userType) " +
                "VALUES ('Bob', 'Builder', 'builder', '12345', '@build', '890674381', '00/00/00', 'random address', 'MANAGER')";
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

    public Cursor selectUser() {
        String query = "SELECT firstName, lastName, username, password, email, phone, birthday, " +
                "address, userType FROM Users";
        return mDb.rawQuery(query, null);
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
        String query = "SELECT username, password, userType FROM Users";
        Cursor c = mDb.rawQuery(query, null);
        try {
            if (c.moveToFirst()){
                do{
                    String user = c.getString(0);
                    String pass = c.getString(1);
                    if (user.equals(username) && pass.equals(password)) {
                        currUserType = c.getString(2);
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
                String command = "create table if not exists Users (_id integer primary key autoincrement, " +
                        "firstName text, lastName text, username text, password text, " +
                        "email text, phone text, birthday text, address text, userType text)";
                mDb.execSQL(command);
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
