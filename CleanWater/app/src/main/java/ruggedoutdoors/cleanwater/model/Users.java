package ruggedoutdoors.cleanwater.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Created by karanachtani on 2/28/17.
 */

public class Users {

    //set up firebase
    private static FirebaseDatabase database;
    private static DatabaseReference mDatabase = database.getInstance().getReference("users");

    private static User toReturn;

    /**
     * adds a user to the map of users
     *
     * @param u user to be added
     */
    public static void add(User u) {
        final User user = u;
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.child(user.getUsername()).setValue(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * checks if user exists in the map
     *
     * @param username of user to check
     * @return whether the user is in the map or not
     */
    public static boolean hasUser(String username) {
        DatabaseReference thisUser = mDatabase.child(username);
        if (thisUser == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * gets a user by username
     *
     * @param username of user to get
     * @return the User object that matches the username passed in
     * @throws NoSuchElementException if the user does not exist in the map
     */
    public static User getUser(final String username) {
        final DatabaseReference thisUser = mDatabase.child(username);
        if (thisUser == null) {
            throw new NoSuchElementException("User not found.");
        }

        thisUser.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // somethings wrong here
                toReturn = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return toReturn;
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
        User u = getUser(username);
        if (u.getPassword().equals(password)) {
            return u;
        } else {
            throw new InvalidParameterException("Password is incorrect.");
        }
    }
}
