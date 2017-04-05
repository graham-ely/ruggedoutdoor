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
 * Created by Karan Achtani on 3/4/16.
 */
public class UserManagementFacade {
    public final static String DEFAULT_BINARY_FILE_NAME = "data.bin";
    public final static String DEFAULT_TEXT_FILE_NAME = "data.txt";
    public final static String DEFAULT_JSON_FILE_NAME = "data.json";

    /**
     * the facade maintains references to any required model classes
     */
    private UserManager um;

    /**
     * Singleton pattern
     */
    private static UserManagementFacade instance = new UserManagementFacade();


    /**
     * private constructor for facade pattern
     */
    private UserManagementFacade() {
        um = new UserManager();
    }

    /**
     * Singleton pattern accessor for instance
     *
     *
     * @return the one and only one instance of this facade
     */
    public static UserManagementFacade getInstance() { return instance; }

    public List<User> getUsersAsList() {
        return um.getUsers();
    }

    public User getUserByUsername(final String username) {
        return um.getUserByUsername(username);
    }

    public void addNewUser(final String fn, final String ln, final String un, final String password, final String email, final String phone, final String bday, final String address, final String type) {
         um.addUser(fn,  ln,  un,  password, email,  phone,  bday,  address,  type);
    }


    public boolean loadText(File file) {
        try {
            //make an input object for reading
            if (!file.isFile()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            um.loadFromText(reader);

        } catch (IOException e) {
            Log.e("ModelSingleton", "Failed to open text file for loading!");
            return false;
        }

        return true;
    }

    public boolean loadJson(File file) {
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            //Since we saved the json as a string, we just read in the string normally
            String inString = input.readLine();
            Log.d("DEBUG", "JSON: " + inString);
            //Then we use the Gson library to recreate the object references and links automagically
            Gson gson = new Gson();

            um = gson.fromJson(inString, UserManager.class);

            input.close();
        } catch (IOException e) {
            Log.e("UserManagementFacade", "Failed to open/read the buffered reader for json");
            return false;
        }

        return true;

    }



    public boolean saveText(File file) {
        System.out.println("Saving as a text file");
        try {
            PrintWriter pw = new PrintWriter(file);
            um.saveAsText(pw);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("UserManagerFacade", "Error opening the text file for save!");
            return false;
        }

        return true;
    }

}
