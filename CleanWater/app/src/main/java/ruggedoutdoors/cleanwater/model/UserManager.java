package ruggedoutdoors.cleanwater.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages the list of students in the model
 * It is all package vis because it should only be accessed from the facade, not directly
 *
 */
class UserManager implements Serializable {

    /**
     * A list of students
     */
    private final List<User> users = new ArrayList<>();

    /**
     * A map of students by Key == user name Value == student object
     *
     * This is marked as transient so it will not be serialized.
     * It is derived from the students collection above, so it does not
     * need to be serialized.
     */
    private transient Map<String, User> userMap = new HashMap<>();


    void addUser(String fn, String ln, String un, String password, String email, String phone, String bday, String address, String type) {
        User user = new User(fn,  ln,  un,  password, email,  phone,  bday,  address,  type);
        users.add(user);
        userMap.put(un, user);
    }


    /**
     * Attempt to login a user
     *
     * @param uid user id of user
     * @param pwd password of user
     * @return  the Student object is login successfull, null otherwise
     */
    User doLogin(String uid, String pwd) {
        // first lookup the user by their login id
        User s = userMap.get(uid);
        //if that user id not there, return null
        if (s == null) return null;
        // we have a good user at this point, so check their password
        if (s.checkPassword(pwd)) return s;
        //return null if a bad password
        return null;
    }


    /**
     * this is package vis because only model should be asking for this data
     *
     * @return
     */
    List<User> getUsers() {
        return users;
    }

    User getUserByUsername(String name) {
        return userMap.get(name);
    }

    /**
     *
     * @param writer
     */
    void saveAsText(PrintWriter writer) {
        System.out.println("Manager saving: " + users.size() + " users" );
        writer.println(users.size());
        for(User s : users) {
            s.saveAsText(writer);
        }
    }

    /**
     * load the model from a custom text file
     *
     * @param reader the file to read from
     */
    void loadFromText(BufferedReader reader) {
        System.out.println("Loading Text File");
        userMap.clear();
        users.clear();
        try {
            String countStr = reader.readLine();
            assert countStr != null;
            int count = Integer.parseInt(countStr);

            //then read in each user to model
            for (int i = 0; i < count; ++i) {
                String line = reader.readLine();
                User s = User.parseEntry(line);
                users.add(s);
                userMap.put(s.getUsername(), s);
            }
            //be sure and close the file
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done loading text file with " + users.size() + " students");

    }


    /**
     * This should only be called during serialization (reading in).
     *
     * This recomputes the student map which is derived from the student collection.
     *
     */
    void regenMap() {
        if (userMap != null)
            userMap.clear();
        else
            userMap = new HashMap<>();
        for (User s : users) {
            userMap.put(s.getUsername(), s);
        }
    }
}