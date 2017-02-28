package ruggedoutdoors.cleanwater;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Created by karanachtani on 2/28/17.
 */

public class Users {
    private static HashMap<String, User> userSet;

    public Users () {
        userSet = new HashMap<>();
    }

    public static void add(User u) {
        userSet.put(u.getUsername(), u);
    }

    public boolean hasUser(String username) {
        return userSet.containsKey(username);
    }

    public User getUser(String username) throws NoSuchElementException {
        if (hasUser(username)) {
            return userSet.get(username);
        } else {
            throw new NoSuchElementException("User not found.");
        }

    }

    public User getUser(String username, String password) {
        if (hasUser(username)) {
            User user = userSet.get(username);
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                throw new InvalidParameterException("Password is incorrect.");
            }
        } else {
            throw new NoSuchElementException("User not found.");
        }
    }


}
