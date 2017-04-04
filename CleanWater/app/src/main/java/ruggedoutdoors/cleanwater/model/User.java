package ruggedoutdoors.cleanwater.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.PrintWriter;

/**
 * Created by karanachtani on 2/27/17.
 */

public class User {

    private String firstName, lastName, username, password, email, phone;
    private String birthday;
    private String address;
    private UserType userType;

    /**
     * Blank constructor required for firebase support
     */
    public User () {}

    /**
     * constructor
     * @param fn first name
     * @param ln last name
     * @param un username
     * @param password password
     * @param email email address
     * @param phone phone number
     * @param bday birthday
     * @param address address
     * @param type UserType of the User
     */
    public User (String fn, String ln, String un, String password, String email, String phone, String bday, String address, String type) {
        firstName = fn;
        lastName = ln;
        username = un;
        this.password = password;
        this.email = email;
        this.phone = phone;
        birthday = bday;
        this.address = address;
        userType = UserType.valueOf(type);
    }

    /**
     * gets first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * gets password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets phone number
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * sets phone number
     * @param phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * gets bday
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * sets birthday
     * @param birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * gets address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * sets address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * gets UserType of user
     * @return userType of user
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * sets UserTyper of user
     * @param userType of user
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Save this class in a custom save format
     * I chose to use tab (\t) to make line splitting easy for loading
     * If your data had tabs, you would need something else as a delimiter
     *
     * @param writer the file to write this student to
     */
    public void saveAsText(PrintWriter writer) {
        System.out.println("Student saving user: " + username);
        writer.println(firstName + "\t" + lastName + "\t" + username + "\t" + password + "\t"
                + email + "\t" + phone + "\t" + birthday + "\t" + address + "\t" + userType.toString());
    }


    /**
     * This is a static factory method that constructs a student given a text line in the correct format.
     * It assumes that a student is in a single string with each attribute separated by a tab character
     * The order of the data is assumed to be:
     *
     * 0 - name
     * 1 - user id
     * 2 - id code
     * 3 - email
     * 4 - password
     *
     * @param line  the text line containing the data
     * @return the student object
     */
    public static User parseEntry(String line) {
        assert line != null;
        String[] tokens = line.split("\t");
        assert tokens.length == 9;
        User u = new User(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8]);

        return u;
    }

    /**
     * check an entered password for a match
     *
     * @pre pwd is not null
     * @param pwd the password to check
     * @return true is passwords match, false otherwise
     */
    public boolean checkPassword(String pwd) {
        return password.equals(pwd);
    }
}
