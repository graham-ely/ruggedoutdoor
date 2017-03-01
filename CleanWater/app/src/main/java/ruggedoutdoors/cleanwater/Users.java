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
    private static HashMap<String, User> userSet = new HashMap<>();

    /**
     * adds a user to the map of users
     *
     * @param u user to be added
     */
    public static void add(User u) {
        userSet.put(u.getUsername(), u);
    }

    /**
     * checks if user exists in the map
     *
     * @param username of user to check
     * @return whether the user is in the map or not
     */
    public static boolean hasUser(String username) {
        return userSet.containsKey(username);
    }

    /**
     * gets a user by username
     *
     * @param username of user to get
     * @return the User object that matches the username passed in
     * @throws NoSuchElementException if the user does not exist in the map
     */
    public static User getUser(String username) {
        if (hasUser(username)) {
            return userSet.get(username);
        } else {
            throw new NoSuchElementException("User not found.");
        }

    }

    /**
     * gets a user by username and password
     *
     * @param username of the user to get
     * @param password of the user to get
     * @return the User object if and only if the user exists and the password matches the username
     * @throws InvalidParameterException if password is incorrect but user was found
     * @throws NoSuchElementException    if user is not found in map
     */
    public static User getUser(String username, String password) {
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
